
$(document).ready(function() {
    getAllOrder();
});

$(document).on('click', '.order-detail-item', function() {
    $(this).toggleClass('order-radius');
    console.log(this);
});

function getAllOrder() {
    $.ajax({
       type: "GET",
       url: "/admin/getAllOrder",
       success: function(orders) {
           let html = '';
           orders.forEach((order) => {
              html +=
                  "<div class='row mt-3 order-detail-item align-items-center'>" +
                  "<div class=\"col-3 h-100\">" +
                  "<div class='shopping-cart-name' style='margin-left: 0;'>"+ order.user.email +"</div>" +
                  "</div>" +
                  "<div class=\"col-9 h-100\">" +
                  "<div class=\"row text-center align-items-center h-100\">" +
                  "<div class=\"shopping-cart-property col-3\">" +
                  "<h4 class=\"shopping-cart-product\">"+ order.address +"</h4>" +
                  "</div>" +
                  "<div class=\"shopping-cart-property col-3\">" +
                  "<form method='post' action='/shop/order' class='d-flex justify-content-center'>" +
                  "<div class='d-flex product-detail_number align-items-center'>" +
                  "<input type='hidden' name='userId' value='1'/>" +
                  "<input type='hidden' name='productId' value='"+ order.phoneNumber +"' disabled/>" +
                  "<h4 class=\"shopping-cart-product\">"+ order.phoneNumber +"</h4>" +
                  "</div>" +
                  "</form>" +
                  "</div>" +
                  "<div class=\"shopping-cart-property col-3\">" +
                  "<h4 class=\"shopping-cart-product\">"+ order.orderNote +"</h4>" +
                  "</div>" +
                  "<div class=\"shopping-cart-property col-3\">" +
                  "<a class=\"btn btn-primary\" href='/admin/confirm/"+ order.id +"'>Confirm</a>" +
                  "</div>" +
                  " </div>" +
                  "</div>" +
                  "</div>";
              html +=
                  '<div class="order-detail-orderItem row">' +
                    renderOrderItem(order.orderItems) +
                  '</div>'
           });
           $('.order-detail').html(html);
       }
    });
}

function renderOrderItem(orderItems) {
    let orderHtml = '';
    orderItems.forEach((orderItem, index) => {
        orderHtml +=
            "<div class='row align-items-center' >" +
            "<div class=\"col-3 h-100\">" +
            "<div class='shopping-cart-name'>"+ orderItem.productOrder.name +"</div>" +
            "</div>" +
            "<div class=\"col-9 h-100\">" +
            "<div class=\"row text-center align-items-center h-100\">" +
            "<div class=\"shopping-cart-property col-3\">" +
            "<h4 class=\"shopping-cart-product\">"+ "$"+ Math.min(orderItem.productOrder.cost, orderItem.productOrder.saleCost).toFixed(2) +"</h4>" +
            "</div>" +
            "<div class=\"shopping-cart-property col-3\">" +
            "<form method='post' action='/shop/order' class='d-flex justify-content-center'>" +
            "<div class='d-flex product-detail_number align-items-center'>" +
                "<h4 class=\"shopping-cart-product\">"+ orderItem.quantity +"</h4>" +
            "</div>" +
            "</form>" +
            "</div>" +
            "<div class=\"shopping-cart-property col-3\">" +
            "<h4 class=\"shopping-cart-product\">"+ (Math.min(orderItem.productOrder.cost, orderItem.productOrder.saleCost) * orderItem.quantity).toFixed(2) +"</h4>" +
            "</div>" +
            " </div>" +
            "</div>" +
            "</div>";
    });
    return orderHtml;
}
