# Movie App

The **Movie App** is a web application developed using Angular 17.3.4. It provides a user-friendly interface for users to browse and interact with movie data.

## Features

- **User Authentication:** Allows users to sign up and log in securely to access personalized features.
- **Movie Browsing:** Enables users to browse a collection of movies, view details, and search for specific titles.
- **Wishlist Management:** Allows users to bookmark their favorite movies and manage their wishlist.
- **Responsive Design:** Optimized for various screen sizes, including desktop and mobile devices.

## Technologies Used

- **Angular 17.3.4**
- **TypeScript**
- **Angular Material** for UI components
- **RxJS** for reactive programming
- **Angular Router** for navigation
- **Angular Forms** for form handling
- **Authentication with JWT**
- **HttpClient** for API communication

## Getting Started

### Prerequisites

Before you begin, ensure you have met the following requirements:

- Node.js and npm installed on your machine.
- Angular CLI installed globally:

  ```bash
  npm install -g @angular/cli@17.3.4
  ```

### Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/yourusername/movie-app.git
   cd movie-app
   ```

2. **Install Dependencies**

   ```bash
   npm install
   ```

### Development Server

Run the following command to start the development server:

```bash
ng serve
```

Navigate to `http://localhost:4200/` in your browser to view the application.

### Building the Application

To build the application for production, run:

```bash
ng build
```

To test the application with coverage, run:

```bash
ng test --code-coverage
```

The production-ready files will be generated in the `dist/` directory. **Client Side Rendering(CSR):** dist/browser. **Server Side Rendering(SSR):** dist/server

The test-coverage files will be generated in the `coverage/` directory.

## Project Structure

```
movie-app
├── src
│   ├── app
│   │   ├── errors
│   │   │   ├── internal-server-error
│   │   │   │   └── ...
│   │   │   ├── no-content
│   │   │   │   └── ...
│   │   │   ├── not-found
│   │   │   │   └── ...
│   │   │   ├── service-unavailable
│   │   │   │   └── ...
│   │   │   └── something-went-wrong
│   │   │   │   └── ...
│   │   ├── model
│   │   │   └── ...
│   │   ├── movie
│   │   │   └── top100-movies
│   │   │   │   └── ...
│   │   ├── navigation
│   │   │   ├── footer
│   │   │   │   └── ...
│   │   │   └── top-bar
│   │   │   │   └── ...
│   │   ├── public
│   │   │   ├── home
│   │   │   │   └── ...
│   │   │   ├── landing
│   │   │   │   └── ...
│   │   │   ├── login
│   │   │   │   └── ...
│   │   │   └── signup
│   │   │   │   └── ...
│   │   ├── service
│   │   │   ├── data
│   │   │   │   └── ...
│   │   │   └── security
│   │   │   │   └── ...
│   │   ├── user
│   │   │   ├── profile
│   │   │   │   └── ...
│   │   │   ├── update-profile
│   │   │   │   └── ...
│   │   │   └── wishlist
│   │   │   │   └── ...
│   │   └── app.routing.ts
│   ├── assets
│   │   └── ...
│   ├── environments
│   │   ├── environment.ts
│   │   ├── environment.prod.ts
│   └── index.html
├── angular.json
├── package.json
└── README.md
```

## Contributing

Contributions are welcome! Please fork this repository and submit pull requests. Thank You.

## License

This project is licensed under the MIT License - see the [LICENSE](./LICENSE) file for details.
