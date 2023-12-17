# Pineapple CLI
#    \|/
#    AXA
#   /XXX\
#   \XXX/
#    `^'
#
# Used to Upgrade Pineapple and Report other useful information
from importlib.machinery import ModuleSpec
import importlib.util
from ui import main_menu

PINEAPPLE: str = """
    \\|/
    AXA
   /XXX\\
   \\XXX/
    `^'
"""

def check_dependencies() -> bool:
    missing_dependencies: bool = True
    with open("requirements.txt", 'r') as file:
        for line in file.readlines():
            line = line.strip()
            module: ModuleSpec = importlib.util.find_spec(line)
            if module is None:
                print(f'pip package of "{line}" is NOT installed')
                missing_dependencies: bool = False
            else:
                print(f'pip package of "{line}" is installed')
    return missing_dependencies

if __name__ == '__main__':
    print(PINEAPPLE)
    print("Checking Dependencies...")
    dependency_status: bool = check_dependencies()
    print("Finished Checking Dependencies")
    if not dependency_status:
        print("Failed to find all dependencies please download them using \"pip install -r requirements.txt\"")
        exit(1)
    
    main_menu.MainMenu()
    