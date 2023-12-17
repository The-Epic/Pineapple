from utils.panel import Panel
from utils.cosmetics import cfiglet, cprint
from impl import pineapple_upgrade as pupgrade
from utils.config import load_config

class VersionConfig():
    
    def __init__(self) -> None:
        self.version_data: dict[str,str] = load_config("data/version_data.json")
        
    def nms_version(self, version: str) -> str:
        return self.version_data[version]

class MainMenu(Panel):
    
    def __init__(self, autoclear=True) -> None:
        self.versions: VersionConfig = VersionConfig()
        options = {
            "1": ("Upgrade", self.pineapple_upgrade)
        }
        super().__init__(options=options, autoclear=autoclear)
        super().start()
    
    def display_figlet(self):
        cfiglet("&e", "Pineapple")
    
    def display_description(self):
        cprint("&7Pineapple is a useful and dynamic spigot library")

    def get_tag(self):
        return "P" 
    
    def pineapple_upgrade(self):
        source_ver: str = input("source version: ")
        target_ver: str = input("target version: ")
        upgrader = pupgrade.Upgrader(self.versions, source_ver, target_ver)
        upgrader.upgrade()
        input()