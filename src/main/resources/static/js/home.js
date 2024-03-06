
$(document).ready(function(){
    getAllProduct(0)
    getTotalPagesAll();
});

function getAllProductByCategory(categoryName, pageNumber) {
    $.ajax({
        type: "GET",
        url: "/getProductListByCategory/" + categoryName + "/" + pageNumber,
        success: function (data) {
            var productHTML = "";
            data.forEach(function (product) {
                productHTML += "<div class=\"feature-product-item col-3 gt-5\">";
                productHTML += "<img src='/getProductImage/" + product.id + "' alt='' class='feature-product-item-img'>";
                productHTML += "<div class=\"feature-product-item-text\">";
                productHTML += "<a class=\"feature-product-item-title text-center d-block\">" + product.name + "</a>";
                productHTML += "<h5 class=\"feature-product-item-cost text-center\">" + "$" + product.cost + ".00</h5>";
                productHTML += "</div>";
                productHTML += "</div>";
            });
            $('.feature-product-item-wrapper').html(productHTML);
        }
    });
}



function getAllProduct(pageNumber) {
    $.ajax({
        type: "GET",
        url: "/getProductList/" + pageNumber,
        success: function (data) {
            var productHTML = "";
            data.forEach(function (product) {
                productHTML += "<div class=\"feature-product-item col-3 gt-5\">";
                productHTML += "<img src='/getProductImage/" + product.id + "' alt='' class='feature-product-item-img'>";
                productHTML += "<div class=\"feature-product-item-text\">";
                productHTML += "<a class=\"feature-product-item-title text-center d-block\">" + product.name + "</a>";
                productHTML += "<h5 class=\"feature-product-item-cost text-center\">" + "$" + product.cost + ".00</h5>";
                productHTML += "</div>";
                productHTML += "</div>";
            });
            $('.feature-product-item-wrapper').html(productHTML);
        }
    });
}

function pageChoose(val) {
    $('.pagination.pagination-sm .page-item').removeClass('active'); // Xóa class active ở tất cả các nút paging
    var newElement = $('.pagination.pagination-sm .product-page').eq(val - 1).addClass('active');
}

function getTotalPagesAll() {
    $.ajax({
        type: "GET",
        url: "/getTotalPagesAll",
        success: function (data) {
            var pageHTML = "";
            for(var i = 1; i <= data; i++) {
                if(i == 1)
                    pageHTML += "<li class=\"page-item active product-page\" aria-current=\"page\">";
                else
                    pageHTML += "<li class=\"page-item product-page\" aria-current=\"page\">";
                pageHTML += "<a class=\"page-link\" onclick='getAllProduct(" + (i - 1) + ");" +
                    "pageChoose(" + i + ")'>" + i + "</a>";
                pageHTML += "</li>";
            }
            $('.pagination.pagination-sm').html(pageHTML);
        }
    });
}


function getTotalPages(categoryName) {
    $.ajax({
        type: "GET",
        url: "/getTotalPages/" + categoryName,
        success: function (data) {
            var pageHTML = "";
            for(var i = 1; i <= data; i++) {
                if(i == 1)
                    pageHTML += "<li class=\"page-item active product-page\" aria-current=\"page\">";
                else
                    pageHTML += "<li class=\"page-item product-page\" aria-current=\"page\">";
                pageHTML += "<a class=\"page-link\" onclick='getAllProductByCategory(\"" + categoryName + "\"," + (i - 1) + ");" +
                    "pageChoose(" + i + ")'>" + i + "</a>";
                pageHTML += "</li>";
            }
            $('.pagination.pagination-sm').html(pageHTML);
        }
    });
}





