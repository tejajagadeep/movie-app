#!/usr/bin/env bash

cd ~../../movie-app

git add .

DATE=$(date)

TIME=$(time)

git commit -m "jagadeep made changes on $TIME $DATE"

git push

osascript -e 'diplay notification "pushed to remote" with title "SUCCESS"'