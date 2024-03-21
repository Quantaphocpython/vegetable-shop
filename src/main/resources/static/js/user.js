
$(document).ready(function() {
    load();
});

function load() {
    let a = document.location.href;
    // let c = $('.category-list-item-link').removeClass('active');

    if (a.includes("purchase")) {
        document.querySelector('#order').classList.add('active');
        getOrderItemList();
    } else if (a.includes("profile")) {
        document.querySelector('#profile').classList.add('active');
    }
}


function getOrderItemList() {
    $.ajax({
        type: "GET",
        url: "/user/getAllUserOrder",
        success: function (userOrders) {
            let orderHtml = "";
            if(userOrders === undefined)
                orderHtml = "<span>Empty</span>"
            let orderItems = [];
            let state = [];
            userOrders.forEach((current, index) => {
                current.orderItems.forEach((orderItem, orderIndex) => {
                    orderItems.push(orderItem);
                    state.push(current.status);
                });
            });
            orderItems.forEach((orderItem, index) => {
                orderHtml +=
                    "<div class='row shopping-cart-row align-items-center'>" +
                    "<div class=\"col-4 h-100\">" +
                    "<img src='/getProductImage/" + orderItem.productOrder.id + "' class='shopping-cart-img'/>" +
                    "<span class='shopping-cart-name'>"+ orderItem.productOrder.name +"</span>" +
                    "</div>" +
                    "<div class=\"col-8 h-100\">" +
                    "<div class=\"row text-center align-items-center h-100\">" +
                    "<div class=\"shopping-cart-property col-3\">" +
                    "<h4 class=\"shopping-cart-product\">"+ "$"+ Math.min(orderItem.productOrder.cost, orderItem.productOrder.saleCost).toFixed(2) +"</h4>" +
                    "</div>" +
                    "<div class=\"shopping-cart-property col-3\">" +
                    "<form method='post' action='/shop/order' class='d-flex justify-content-center'>" +
                    "<div class='d-flex product-detail_number align-items-center'>" +
                    "<input type='hidden' name='userId' value='1'/>" +
                    "<input type='hidden' name='productId' value='"+ orderItem.productOrder.id +"' disabled/>" +
                    "<div style='font-weight: bold'>"+ orderItem.quantity +"</div>" +
                    "</div>" +
                    "</form>" +
                    "</div>" +
                    "<div class=\"shopping-cart-property col-3\">" +
                        "<h4 class=\"shopping-cart-product\" style='color: #DD2222'>"+ state[index] +"</h4>" +
                    "</div>" +
                    "<div class=\"shopping-cart-property col-3\">" +
                        "<a class=\"btn btn-primary\" href='/shop/product/"+ orderItem.productOrder.id +"'>Buy again</a>" +
                    "</div>" +
                    " </div>" +
                    "</div>" +
                    "</div>";
            });
            $('.order-item-wrapper').html(orderHtml);
        },
        error: function (a) {
            $('.order-item-wrapper').html("<h5 class='text-center mt-5 mb-5' style='color: rgba(0, 0, 0, 0.5); font-size: 18px'>Empty</h5>");
        }
    });
}


function categoryChoose(val) {
    $('.category-list-item-link').removeClass('active'); // Xóa class active ở tất cả các nút paging
    $('.category-list-item').eq(val).find('.category-list-item-link').addClass('active');
}

