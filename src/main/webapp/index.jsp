<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Music List</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
            loadMusicList();
        });

        function loadMusicList() {
            $.getJSON("JSONServlet", function(data) {
                var MusicList = $("#Music-list");
                MusicList.empty();
                $.each(data, function() {
                    var Music = this;
                    if (Music.name !== undefined) {
                        MusicList.append("<tr>" +
                            "<td>" + Music.name + "</td>" +
                            "<td>" + Music.author + "</td>" +
                            "<td>" + Music.year + "</td>" +
                            "<td>" + Music.album + "</td>" +
                            "<td>" + Music.country + "</td>" +
                            "</tr>");
                    }
                });
            });
        }

        function addMusic() {
            var name = $("#name").val();
            var author = $("#author").val();
            var year = parseInt($("#year").val());
            var album = $("#album").val();
            var country = parseFloat($("#country").val());

            // Create a JSON object with Music properties
            var Music = {
                "name": name,
                "author": author,
                "year": year,
                "album": album,
                "country": country
            };

            $.ajax({
                url: "JSONServlet",
                type: "POST",
                data: JSON.stringify(Music),
                contentType: "application/json",
                success: function(data) {
                    loadMusicList();
                },
                error: function(error) {
                    console.log(error);
                    alert("Error: " + error.statusText);
                }
            });
        }
    </script>
    <style>
        body {
            font-family: Arial, sans-serif;
            font-size: 16px;
        }

        table {
            border-collapse: collapse;
            width: 100%;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-album: #4CAF50;
            album: white;
        }

    </style>
</head>
<body>
<h1>Music List</h1>
<table id="Music-list" border="1">
    <tr>
        <th>name</th>
        <th>author</th>
        <th>Year</th>
        <th>album</th>
        <th>country</th>
    </tr>
</table>
<h2>Add a Music</h2>
<form id="add-Music-form" onsubmit="event.preventDefault(); addMusic();">
    <table>
        <tr>
            <td><label for="name">name:</label></td>
            <td><input type="text" id="name"></td>
        </tr>
        <tr>
            <td><label for="author">author:</label></td>
            <td><input type="text" id="author"></td>
        </tr>
        <tr>
            <td><label for="year">Year:</label></td>
            <td><input type="number" id="year"></td>
        </tr>
        <tr>
            <td><label for="album">album:</label></td>
            <td><input type="text" id="album"></td>
        </tr>
        <tr>
            <td><label for="country">country:</label></td>
            <td><input type="number" id="country" step="0.01"></td>
        </tr>
        <tr>
            <td></td>
            <td><button type="submit">Add</button></td>
        </tr>
    </table>
</form>
</body>
</html>
