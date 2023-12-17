# Pineapple Upgrade
#    \|/
#    AXA
#   /XXX\
#   \XXX/
#    `^'
#

import os
import shutil
import fileinput
import sys

GRADLE_SETTINGS: str = 'settings.gradle'
SETTINGS_ENTRIES: list[str] = ["include 'pineapple-nms:spigot-v{version}", "findProject(':pineapple-nms:spigot-v{version}')?.name = 'spigot-{version}'"]
NMS_DIRECTORY_PATH: str = '../pineapple-nms'
NMS_MODULE_PATH: str = NMS_DIRECTORY_PATH + '/spigot-v{path}'

COPY_FILES: list[str] = ['src', 'build.gradle']

class Upgrader():
    
    def __init__(self, config, old_version: str, new_version: str):
        self.config = config
        self.old_version: str = old_version
        self.new_version: str = new_version
        
    def upgrade(self):
        old_nms_version:str = self.config.nms_version(self.old_version)
        new_nms_version:str = self.config.nms_version(self.new_version)
        old_path: str = NMS_MODULE_PATH.format(path=old_nms_version)
        new_path: str = NMS_MODULE_PATH.format(path=new_nms_version)
        shutil.copytree(old_path, new_path, ignore=shutil.ignore_patterns("build"))
        os.chdir(new_path)
        walk_new_version("v"+old_nms_version, "v"+new_nms_version)
        rename_build_gradle(self.old_version, self.new_version)
        os.chdir("../../")
        insert_module(old_nms_version, new_nms_version)
        

def walk_new_version(target: str, replacement: str):
    directories: list[str] = []
    for root, dirs, files in os.walk("."):
        for file in files:
            if file == 'build.gradle':
                continue
            rename_files(os.path.join(root, file), target, replacement)
        
        for dir in dirs:
            if target not in dir:
                continue
            directories.append(os.path.join(root, dir))
    
    for dir_abs in directories:
        shutil.copytree(dir_abs, dir_abs.replace(target, replacement))
        shutil.rmtree(dir_abs)


def rename_files(file_name: str, target: str, replacement: str):
    for line in fileinput.input(file_name, inplace=True):
        sys.stdout.write(line.replace(target, replacement))
        
def rename_build_gradle(target: str, replacement: str):
    file_input: fileinput.FileInput[str] = fileinput.input("build.gradle", inplace=True)
    for line in file_input:
        sys.stdout.write(line.replace(target, replacement))
        
def insert_module(target: str, replacement: str):
    for line in fileinput.input("settings.gradle", inplace=True):
        sys.stdout.write(line)
        if target not in line:
            continue
        sys.stdout.write(line.replace(target, replacement))