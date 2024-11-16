window.onload = function() {
    const comicsList = document.getElementById("comics-list");

    // Example Marvel API URL (Replace with your actual API URL and key)
    const marvelApiUrl = "https://gateway.marvel.com/v1/public/comics?apikey=YOUR_API_KEY";
    
    fetch(marvelApiUrl)
        .then(response => response.json())
        .then(data => {
            const comics = data.data.results;
            if (comics && comics.length > 0) {
                comicsList.innerHTML = comics.map(comic =>
                    `<div class="comic-item">
                        <h3>${comic.title}</h3>
                        <img src="${comic.thumbnail.path}.${comic.thumbnail.extension}" alt="${comic.title}" />
                    </div>`
                ).join('');
            } else {
                comicsList.innerHTML = "<p>No comics available at the moment.</p>";
            }
        })
        .catch(error => {
            comicsList.innerHTML = "<p>Error fetching comics data.</p>";
            console.error(error);
        });
};
