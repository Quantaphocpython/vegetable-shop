
$(document).ready(function() {
    getOrderItemList();
});

function getOrderItemList() {
    $.ajax({
        type: "GET",
        url: "/shop/getOrderItemList",
        success: function (orderItems) {
            let orderHtml = "";
            console.log(orderItems)
            if(orderItems === undefined)
                orderHtml = "<span>Empty</span>"
            orderItems.forEach((orderItem, index) => {
                orderHtml +=
                    "<div class='row shopping-cart-row align-items-center'>" +
                        "<div class=\"col-6 h-100\">" +
                            "<img src='/getProductImage/" + orderItem.productOrder.id + "' class='shopping-cart-img'/>" +
                            "<span class='shopping-cart-name'>"+ orderItem.productOrder.name +"</span>" +
                        "</div>" +
                        "<div class=\"col-6 h-100\">" +
                            "<div class=\"row text-center align-items-center h-100\">" +
                                "<div class=\"shopping-cart-property col-4\">" +
                                    "<h4 class=\"shopping-cart-product\">"+ "$"+ Math.min(orderItem.productOrder.cost, orderItem.productOrder.saleCost).toFixed(2) +"</h4>" +
                                "</div>" +
                                "<div class=\"shopping-cart-property col-4\">" +
                                    "<form method='post' action='/shop/order' class='d-flex justify-content-center'>" +
                                        "<div class='d-flex product-detail_number align-items-center'>" +
                                            "<span class='product-detail_number-item' onclick='decreaseProductQuantity("+ index +"); changeQuantity("+ orderItem.id +")'>-</span>" +
                                            "<input type='hidden' name='userId' value='1'/>" +
                                            "<input type='hidden' name='productId' value='"+ orderItem.productOrder.id +"'/>" +
                                            "<input class='product-detail_number-item text-center product-quantity-value' type='number' value='"+ orderItem.quantity +"' " +
                                                    "name='quantity' data-id='"+ orderItem.id +"' onkeyup='changeQuantity("+ orderItem.id +")''/>" +
                                            "<span class='product-detail_number-item' onclick='incrementProductQuantity("+ index +"); changeQuantity("+ orderItem.id +")''>+</span>" +
                                        "</div>" +
                                    "</form>" +
                                "</div>" +
                                "<div class=\"shopping-cart-property col-4\">" +
                                    "<h4 class=\"shopping-cart-product\">"+ (Math.min(orderItem.productOrder.cost, orderItem.productOrder.saleCost) * orderItem.quantity).toFixed(2) +"</h4>" +
                                "</div>" +
                                "<div class='shopping-cart-item-delete' onclick='deleteOrderItemById("+ orderItem.id +")'>" +
                                    "<i class=\"bi bi-x-lg\"></i>" +
                                "</div>" +
                            " </div>" +
                        "</div>" +
                    "</div>";
            });
            $('.order-item-wrapper').html(orderHtml);
            getTotalPrice();
        },
        error: function () {
            $('.order-item-wrapper').html("<h5 class='text-center mt-5 mb-5' style='color: rgba(0, 0, 0, 0.5); font-size: 18px'>Empty</h5>");
            getTotalPrice();
        }
    });
}

function changeQuantity(orderItemId) {
    let quantity = document.querySelector(`[data-id="${orderItemId}"]`).value;
    $.ajax({
        type: "patch",
        url: "/shop/changeQuantity",
        data: {
            id: orderItemId,
            quantity: quantity
        },
        success: function (rs) {
            console.log(rs);
            getTotalPrice();
        }
    })
}

function getTotalPrice() {
    $.ajax({
        type: "GET",
        url: "/shop/getTotalPrice",
        success: function(price) {
            $('.cart-total-price').text('$' + price.toFixed(2));
        }
    })
}

function deleteOrderItemById(val) {
    $.ajax({
        type: "delete",
        url: "/shop/deleteOrderItemById",
        data: {
            id: val
        },
        success: function (result) {
            getOrderItemList();
        }
    });
}


function incrementProductQuantity(index) {
    let quantity = document.querySelectorAll(`.product-detail_number`)[index].querySelector('.product-quantity-value');
    quantity.value = parseInt(quantity.value) + 1;
}

function decreaseProductQuantity(index) {
    let quantity = document.querySelectorAll(`.product-detail_number`)[index].querySelector('.product-quantity-value');
    if(quantity.value === "1") {
        quantity.value = 1;
    } else {
        quantity.value = parseInt(quantity.value) - 1;
    }
}