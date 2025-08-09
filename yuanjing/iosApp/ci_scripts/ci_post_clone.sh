#!/bin/sh
brew install mise
brew --prefix mise
MISE_PATH=$(brew --prefix mise)
export PATH="$MISE_PATH/bin/:$PATH"
mise --version

brew install openjdk@21
java -version
