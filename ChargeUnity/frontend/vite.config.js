import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  define: {
    __API_URL__: JSON.stringify(process.env.VITE_APP_API_URL || 'http://localhost:8080/api/v1'),
  },
  server: {
    port: 5173,
    open: true,
  },
});