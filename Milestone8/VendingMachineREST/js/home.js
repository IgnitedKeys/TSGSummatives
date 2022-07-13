var itemsDiv = $('#itemInfoDiv');
var totalMoney = 0;
var clickedItem = "";
$(document).ready(function () {

    $('#addDollarBtn').click(function (event) {
        totalMoney++;
        addMoney(totalMoney);
    });

    $('#addQuarterBtn').click(function (event) {
        totalMoney = totalMoney + .25;
        addMoney(totalMoney);
    });

    $('#addDimeBtn').click(function (event) {
        totalMoney = totalMoney + 0.10;
        addMoney(totalMoney);
    });

    $('#addNickelBtn').click(function (event) {
        totalMoney = totalMoney + .05;
        addMoney(totalMoney);
    });


    getAllItems();
    addMoney();
    returnChange();

    $('#makePurchaseBtn').click(function (event) {
        makePurchase();
    });

    $('#changeReturnBtn').click(function (event) {
        returnChange();
    });
});

function getAllItems() {
    clearVendingItems();
    $.ajax({
        type: 'GET',
        url: 'http://vending.us-east-1.elasticbeanstalk.com/items',
        success: function (itemsArray) {
            $.each(itemsArray, function (index, item) {
                var itemIndex = index + 1;
                var name = item.name;
                var price = item.price;
                var quantity = item.quantity;
                var id = item.id;

                var div = '<div class="itemCard" id="itemId"' + id + '"><button type="button"  onclick="selectItem(' + id + ')" class="itemBtn" id="' + itemIndex + '"><span class="top-left">';
                div += itemIndex + '</span><span class="content">';
                div += name + '<br><br>';
                div += '$' + price + '</span>';
                div += '<span class="amount">Quantity Left: ' + quantity;
                div += '</span></button></div>';

                itemsDiv.append(div);
            })

        },
        error: function () {

        }
    });
}

function clearVendingItems() {
    $('#itemInfoDiv').empty();
}

function selectItem(selectItemId) {
    $('.itemBtn').click(function (event) {
        clearFormAfterSuccess();
        clearFromAfterChange();
        var choosenItem = this.id;
        var itemId = 0;
        $('#showItemNumber').val(choosenItem);
        clickedItem = selectItemId;
    })
}

function addMoney(amount) {
    clearFormAfterSuccess();
    clearFromAfterChange();
    $('#totalMoney').val(amount.toFixed(2));
}

function makePurchase() {

    clearFromAfterChange();
    $.ajax({
        type: 'POST',
        url: 'http://vending.us-east-1.elasticbeanstalk.com/money/' + $('#totalMoney').val() + '/item/' + clickedItem + '',
        'dataType': 'json',
        success: function (data) {
            $('#showMessage').val('Thank You!!');
            $('#totalMoney').val('');
            $('#showItemNumber').val('');

            var quarters = data.quarters;
            var dimes = data.dimes;
            var nickels = data.nickels;
            var pennies = data.pennies;

            var message = "";
            if (quarters == 1) {
                message = '1 quarter';
            } else if (quarters > 1) {
                message = quarters + ' quarters';
            }

            if ((quarters >= 1) && dimes >= 1 || (quarters >= 1) && nickels >= 1 || (quarters >= 1) && pennies >= 1) {
                message += ', ';
            }
            if (dimes == 1) {
                message += '1 dime';
            } else if (dimes > 1) {
                message += dimes + ' dimes';
            }

            if ((dimes >= 1 && nickels >= 1) || (dimes >= 1 && pennies >= 1)) {
                message += ', ';
            }
            if (nickels == 1) {
                message += '1 nickel';
            } else if (nickels > 1) {
                message += nickels + ' nickels';
            }

            if (nickels >= 1 && pennies >= 1) {
                message += ', ';
            }
            if (pennies == 1) {
                message += '1 penny';
            } else if (pennies > 1) {
                message += pennies + ' penny';
            }

            $('#getChange').val(message);
            totalMoney = 0;
            getAllItems();
        },
        error: function (response) {
            if (clickedItem == "") {
                $('#showMessage').val("Please make a selection.");
            }
            if (totalMoney == 0 && clickedItem != "") {
                $('#showMessage').val("Please make a selection.");
            }
            var errorMessage = response.responseJSON.message;
            $('#showMessage').val(errorMessage);

            getAllItems();
        }
    });

}

function returnChange() {

    var money = $('#totalMoney').val();

    if (money == 0) {
        $('#getChange').val("");
    }

    var quarters = 0;
    var dimes = 0;
    var nickels = 0;
    var pennies = 0;

    while (money > "0") {
        if (money > "0.24") {
            money = money - "0.25";
            quarters++;
        }
        else if (money > "0.09") {
            money = money - "0.10";
            dimes++;
        }
        else if (money > "0.04") {
            money = money - "0.05";
            nickels++;
        }
        else {

            pennies = money;
            money = 0;
        }

    }


    var message = "";
    if (quarters == 1) {
        message = '1 quarter';
    } else if (quarters > 1) {
        message = quarters + ' quarters';
    }

    if ((quarters >= 1) && dimes >= 1 || (quarters >= 1) && nickels >= 1 || (quarters >= 1) && pennies >= 1) {
        message += ', ';
    }
    if (dimes == 1) {
        message += '1 dime';
    } else if (dimes > 1) {
        message += dimes + ' dimes';
    }

    if ((dimes >= 1 && nickels >= 1) || (dimes >= 1 && pennies >= 1)) {
        message += ', ';
    }
    if (nickels == 1) {
        message += '1 nickel';
    } else if (nickels > 1) {
        message += nickels + ' nickels';
    }

    if (nickels >= 1 && pennies >= 1) {
        message += ', ';
    }
    if (pennies == 1) {
        message += '1 penny';
    } else if (pennies > 1) {
        message += pennies + ' penny';
    }

    $('#getChange').val(message);
    $('#showItemNumber').val("");
    $('#showMessage').val("");
    totalMoney = 0;
    clickedItem = "";
    $('#totalMoney').val('0.00');

}

function clearFormAfterSuccess() {
    if ($('#showMessage').val() == 'Thank You!!') {
        $('#showMessage').val('');
        $('#getChange').val('');
    }
}

function clearFromAfterChange() {
    if ($('#getChange').val() != "") {
        $('#getChange').val("");
    }
}

