#!/bin/sh

# Decrypt the file
# --batch to prevent interactive command --yes to assume "yes" for questions
gpg --quiet --batch --yes --decrypt --passphrase="$RELEASE_PASSPHRASE" --output keystore.properties keystore.properties.gpg
gpg --quiet --batch --yes --decrypt --passphrase="$GOOGLE_PASSPHRASE" --output ./app/google-services.json ./app/google-services.json.gpg
