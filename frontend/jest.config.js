module.exports = {
  preset: "react-app",
  testEnvironment: "jsdom",
  transform: {
    "^.+\\.(js|jsx|ts|tsx)$": "babel-jest",
  },
  transformIgnorePatterns: [
    "/node_modules/(?!axios)", // Transform axios or any other modules causing issues
  ],
  moduleFileExtensions: ["js", "jsx", "json", "node"],
  setupFilesAfterEnv: ["<rootDir>/src/setupTests.js"],
};
