window.multiUpload = {
    uploadFiles: async function (inputId, uploadUrl) {

        const input = document.getElementById(inputId);
        if (!input || !input.files || input.files.length === 0) {
            return [];
        }

        const formData = new FormData();
        for (const file of input.files) {
            formData.append("files", file, file.name);
        }

        const response = await fetch(uploadUrl, {
            method: "POST",
            body: formData
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error("Upload failed: " + errorText);
        }

        return await response.json();
    }
};