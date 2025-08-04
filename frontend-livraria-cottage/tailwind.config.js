module.exports = {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        cafe: "#6F4E37",
        creme: "#FFF8F0",
        menta: "#B2C8BA",
        bege: "#EADBC8",
        dourado: "#D6A95C"
      },
      fontFamily: {
        serif: ['"Playfair Display"', "serif"],
        sans: ['"Open Sans"', "sans-serif"]
      }
    },
  },
  plugins: [],
};