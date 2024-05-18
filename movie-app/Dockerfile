FROM node:20.10.0-alpine3.17 as builder
WORKDIR /app

COPY package*.json ./
RUN npm install --force

COPY . .
RUN npm run build --prod

# Stage 2: Serve Angular application using Nginx
FROM nginx:alpine

# ENV NGINX_PORT=$NGINX_PORT

COPY /nginx.conf /etc/nginx/conf.d/default.conf

# Copy built Angular artifacts to Nginx directory
COPY --from=builder /app/dist/movie-app/browser /usr/share/nginx/html

# Ensure the custom index.server.html is used
# RUN mv /usr/share/nginx/html/index.server.html /usr/share/nginx/html/index.html

# Expose port 80 to allow external access
EXPOSE 4201

# Start Nginx server
CMD ["nginx", "-g", "daemon off;"]