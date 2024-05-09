FROM node:20.10.0-alpine3.17 as builder
WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build

# Stage 2: Serve Angular application using Nginx
FROM nginx:alpine

COPY /nginx.conf /etc/nginx/conf.d/default.conf

# Copy built Angular artifacts to Nginx directory
COPY --from=builder /app/dist/movie-app/browser /usr/share/nginx/html

# Expose port 4201 to allow external access
EXPOSE 4201

# Start Nginx server
CMD ["nginx", "-g", "daemon off;"]