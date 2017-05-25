<?php

?>
<!--
title
subtitle
minPlayers
maxPlayers
releaseDate
type
publisher
description
ageRating
-->


<form method="post" action="addsubmit.php">
    <table>
        <tr>
            <td>Title</td>
            <td><input type="text" name="title" size="50" maxlength="50"></td>
        </tr>
        <tr>
            <td>Subtitle</td>
            <td><input type="text" name="subtitle" size="100" maxlength="100"></td>
        </tr>
        <tr>
            <td>Min Players</td>
            <td><input type="text" name="minPlayers" size="3" maxlength="3"></td>
        </tr>
        <tr>
            <td>Max Players</td>
            <td><input type="text" name="maxPlayers" size="3" maxlength="3"></td>
        </tr>
        <tr>
            <td>Release Date</td>
            <td><input type="text" name="releaseDate" size="10" maxlength="10"></td>
        </tr>
        <tr>
            <td>Type</td>
            <td>
                <select name="type">
                    <option value="board">Board</option>
                    <option value="video">Video</option>
                    <option value="card">Card</option>
                    <option value="table">Table</option>
                    <option value="other">Other</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>Publisher</td>
            <td></td>
        </tr>
        <tr>
            <td>Description</td>
            <td><textarea name="description" cols="80" rows="6"></textarea></td>
        </tr>
        <tr>
            <td>Age Rating</td>
            <td>
                <select name="ageRating">
                    <option value="all_ages" selected>All Ages</option>
                    <option value="kid">Kid</option>
                    <option value="adult">Adult</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>Save Record</td>
            <td><input type="submit" value="Add Record"/></td>
        </tr>
    </table>
    
</form>

