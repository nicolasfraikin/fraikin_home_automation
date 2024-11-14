import os
import shutil


def replace_symlinks_with_files(base_dir):
    for root, dirs, files in os.walk(base_dir):
        for name in files + dirs:
            path = os.path.join(root, name)

            # Check if the item is a symbolic link
            if os.path.islink(path):
                target_path = os.readlink(path)

                # Resolve the absolute path of the target
                abs_target_path = os.path.abspath(os.path.join(root, target_path))

                # Replace the symbolic link with the actual file or directory content
                if os.path.isfile(abs_target_path):
                    os.remove(path)
                    shutil.copy2(abs_target_path, path)  # Copy file to replace symlink
                    print(f"Replaced symlink with file: {path}")
                elif os.path.isdir(abs_target_path):
                    os.remove(path)
                    shutil.copytree(abs_target_path, path)  # Copy directory to replace symlink
                    print(f"Replaced symlink with directory: {path}")

# Example usage:
# base_directory = "/home/nico/mnt5/only_script"
# replace_symlinks_with_files(base_directory)
