FROM docker:latest

# Install dependencies
RUN apk update && apk add --no-cache \
    python3 \
    py3-pip \
    curl

# Install Docker Compose
RUN curl -L https://github.com/docker/compose/releases/download/v2.27.0/docker-compose-linux-x86_64 -o /usr/local/bin/docker-compose && \
    chmod +x /usr/local/bin/docker-compose

# Set the working directory
WORKDIR /app

# Copy your Docker Compose files
COPY docker-compose.yml /app/docker-compose.yml

# Default command
CMD sh -c "docker-compose pull && docker-compose up"
