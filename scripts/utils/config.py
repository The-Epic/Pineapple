import json

def load_config(config_name: str) -> any:
    with open(config_name, 'r') as f:
        return json.load(f)


def save_config(config, config_name: str):
    with open(config_name, 'w') as f:
        json.dump(config, f)