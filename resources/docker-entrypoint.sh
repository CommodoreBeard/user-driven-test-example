#!/bin/sh

# Start the virtual display for chrome to attach to
sudo Xvfb :10 -ac -screen 0 1024x768x8 &
export DISPLAY=:10

cd ${WORKING_DIR}
exec "$@"