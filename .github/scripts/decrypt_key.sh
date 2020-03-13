#!/bin/sh

# Decrypt the file
chmod +x keystore.properties.gpg
# --batch to prevent interactive command --yes to assume "yes" for questions
gpg --quiet --batch --yes --decrypt --passphrase="$RELEASE_PASSPHRASE" --output . keystore.properties.gpg
