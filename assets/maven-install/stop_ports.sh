#!/bin/bash

# List of ports to stop applications running on
ports=(
    8761
    8888
    8765
    8769
    8080
    8090
    8092
    8081
    8082
    # Add more ports as needed
)

# Function to stop applications running on specific ports
stop_applications() {
    local port="$1"
    
    echo "Stopping applications running on port $port"
    
    # Get the PID of the process using the port
    pid=$(netstat -a -n -o | grep LISTENING | grep ":$port" | awk '{print $5}' | cut -d: -f2 | uniq)
    
    if [ -n "$pid" ]; then
        # Terminate the process
        taskkill //F //PID $pid
        echo "Process with PID $pid stopped"
    else
        echo "No process found running on port $port"
    fi
}

# Loop through each port and stop applications
for port in "${ports[@]}"; do
    stop_applications "$port"
done

echo "All applications running on specified ports stopped"
