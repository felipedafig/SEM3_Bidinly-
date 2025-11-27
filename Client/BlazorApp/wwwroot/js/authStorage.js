(function () {
    const cookieName = "BidinlyAuth";

    window.authStorage = {
        saveToken: (token) => localStorage.setItem("jwt", token),
        getToken: () => localStorage.getItem("jwt"),
        clearToken: () => localStorage.removeItem("jwt")
    };
})();

