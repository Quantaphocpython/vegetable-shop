
function formatNumberWithDecimal(value, decimalPlaces) {
    if (typeof value !== 'number') {
        throw new Error('Giá trị đầu vào không phải là một số.');
    }

    return value.toFixed(decimalPlaces);
}


function getProductSaleOffList(categoryName) {
    $.ajax({
        type: "GET",
        url: "/shop/getProductSaleOffList/" + categoryName,
        success: function (data) {
            var productHTML = "";
            data.forEach(function (product) {
                productHTML += "<div class=\"feature-product-item\">";
                productHTML += "<img src='/getProductImage/" + product.id + "' alt='' class='feature-product-item-img'>";
                productHTML += "<span class='feature-product-item-sale_percent'>-"+ product.salePercent + "%</span>";
                productHTML += "<div class=\"feature-product-item-text\">";
                productHTML += "<a class=\"feature-product-item-title text-center d-block\">" + product.name + "</a>";
                productHTML += "<div class='text-center'>"
                productHTML += "<h5 class=\"feature-product-item-cost d-inline-block\">" + "$" + formatNumberWithDecimal(product.saleCost, 2) + "</h5>";
                productHTML += "<span class='feature-product-item-cost-prev d-inline-block'>$"+ formatNumberWithDecimal(product.cost, 2) +"</span>";
                productHTML += "</div>"
                productHTML += "</div>";
                productHTML += "</div>";
            });
            $('.sale-off-product').html(productHTML);
            $('.owl-carousel.sale-off-product').owlCarousel({
                loop: true,
                margin: 10,
                nav: false,
                dots: true,
                autoplay: true,
                autoplayTimeout:4000,
                smartSpeed: 1200,
                autoplayHoverPause:true,
                time: 2000,
                responsive:{
                    0:{
                        items:1
                    },
                    600:{
                        items:2
                    },
                    1000:{
                        items:3
                    }
                }
            });
        }
    });
}



$(document).ready(function(){
    getProductSaleOffList("ALL");
});