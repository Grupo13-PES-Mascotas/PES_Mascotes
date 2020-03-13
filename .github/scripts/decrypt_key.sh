#!/bin/sh

# Decrypt the file
cd ../../
# --batch to prevent interactive command --yes to assume "yes" for questions
gpg --quiet --batch --yes --decrypt --passphrase="$RELEASE_PASSPHRASE" --output keystore.properties keystore.properties.gpg
