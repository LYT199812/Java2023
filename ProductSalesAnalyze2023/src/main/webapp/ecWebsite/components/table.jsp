<!DOCTYPE html>

<head>
    <script src="components/jquery-2.0.3.js"></script>
    <script src='components/multifilter.js'></script>
    <link href='components/style.css' rel='stylesheet' type='text/css' />
</head>
<script type='text/javascript'>
    $(document).ready(function () {
        $('.filter').multifilter()
    })
</script>

<body>

    <div class='container'>
        <div class='filters'>
            <div class='filter-container'>
                <input autocomplete='off' class='filter' name='name' placeholder='model' data-col='model' />
            </div>
            <div class='filter-container'>
                <input autocomplete='off' class='filter' name='drink' placeholder='productName' data-col='productName' />
            </div>
            <div class='filter-container'>
                <input autocomplete='off' class='filter' name='pizza' placeholder='productType' data-col='productType' />
            </div>
            <div class='filter-container'>
                <input autocomplete='off' class='filter' name='movie' placeholder='brand' data-col='brand' />
            </div>
            <div class='filter-container'>
                <input autocomplete='off' class='filter' name='movie' placeholder='saleStartDate' data-col='saleStartDate' />
            </div>
            <div class='filter-container'>
                <input autocomplete='off' class='filter' name='movie' placeholder='saleEndDate' data-col='saleEndDate' />
            </div>
            <div class='clearfix'></div>
        </div>
    </div>
    <div class='container'>

        <table>
            <thead>
                <th>Model</th>
                <th>productName</th>
                <th>productType</th>
                <th>Brand</th>
                <th>saleStartDate</th>
                <th>saleEndDate</th>
            </thead>
            <tbody>
                <tr>
                    <td>Homer</td>
                    <td>Squishie</td>
                    <td>廚房用品</td>
                    <td>The Avengers</td>
                    <td>The Avengers</td>
                    <td>The Avengers</td>
                </tr>
                <tr>
                    <td>Marge</td>
                    <td>Squishie</td>
                    <td>旅遊用品</td>
                    <td>The Avengers</td>
                    <td>The Avengers</td>
                    <td>The Avengers</td>
                </tr>
                <tr>
                    <td>Bart</td>
                    <td>Squishie</td>
                    <td>烘焙用品</td>
                    <td>Black Dynamite</td>
                    <td>Black Dynamite</td>
                    <td>Black Dynamite</td>
                </tr>
            </tbody>
        </table>
    </div>
</body>

</html>