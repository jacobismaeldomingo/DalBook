import axios from "axios";

export const makeRequest = axios.create({
    baseURL: "http://localhost:8086/api/",
    withCredentials: true,
});