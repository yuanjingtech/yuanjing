#!/bin/sh
brew install mise
brew --prefix mise
MISE_PATH=$(brew --prefix mise)
export PATH="$MISE_PATH/bin/:$PATH"
mise --version
