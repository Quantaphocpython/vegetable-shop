

$(document).ready(function(){
    loadProductDetailPage();
    showMessage();
});

function loadProductDetailPage() {
    let path = document.location.href;
    let match = path.match(/\/product\/(\d+)/);
    let productId = match ? match[1] : 1;

    console.log(document.location);
    getProductDetail(productId);
}


function getProductDetail(id) {
    $.ajax({
        type: "GET",
        url: "/shop/productDetail/" + id,
        success: function (product) {
            let productHTML = "";
            productHTML +=
                "<div class='row product-detail_top'>" +
                    "<div class='col-6 product-detail_img'><img class='w-100 h-100' src='/home/getProductImage/"+ product.id +"' /></div>" +
                    "<div class='col-6 product-detail_top-left'>" +
                        "<h4 class='product-detail_name'>"+ product.name +"</h4>" +
                        "<div class='d-flex'>" +
                            "<i class=\"bi product-detail_star "+ (product.starAverage >= 1 ? 'bi-star-fill' : (product.starAverage > 0  ? 'bi-star-half' : 'bi-star')) +"\"></i>" +
                            "<i class=\"bi product-detail_star "+ (product.starAverage >= 2 ? 'bi-star-fill' : (product.starAverage > 1  ? 'bi-star-half' : 'bi-star')) +"\"></i>" +
                            "<i class=\"bi product-detail_star "+ (product.starAverage >= 3 ? 'bi-star-fill' : (product.starAverage > 2  ? 'bi-star-half' : 'bi-star')) +"\"></i>" +
                            "<i class=\"bi product-detail_star "+ (product.starAverage >= 4 ? 'bi-star-fill' : (product.starAverage > 3  ? 'bi-star-half' : 'bi-star')) +"\"></i>" +
                            "<i class=\"bi product-detail_star "+ (product.starAverage >= 5 ? 'bi-star-fill' : (product.starAverage > 4  ? 'bi-star-half' : 'bi-star')) +"\"></i>" +
                            "<span class='product-detail_total-review'> ("+ product.totalReviews +" reviews)</span>" +
                        "</div>" +
                        "<h4 class='mt-3 cost-color' style='font-size: 30px'>  $"+ Math.min(product.cost, product.saleCost).toFixed(2) + (product.isSale ? "<span style='font-size: 14px; margin-left: 10px'>(sale)</span>" : "") +"</h4>" +
                        "<p class='product-detail_text mt-3'>Mauris blandit aliquet elit, eget tincidunt nibh pulvinar a. Vestibulum ac diam sit amet quam vehicula elementum sed sit amet dui. Sed porttitor lectus nibh. Vestibulum ac diam sit amet quam vehicula elementum sed sit amet dui. Proin eget tortor risus.</p>" +
                        "<div class='d-flex product-detail_number-wrapper mt-5'>" +
                            "<form method='post' action='/shop/order' class='d-flex'>" +
                                "<div class='d-flex product-detail_number'>" +
                                    "<span class='product-detail_number-item' onclick='decreaseProductQuantity()'>-</span>" +
                                    "<input type='hidden' name='userId' value='1'/>" +
                                    "<input type='hidden' name='productId' value='"+ product.id +"'/>" +
                                    "<input class='product-detail_number-item text-center' type='number' value='1' name='quantity'/>" +
                                    "<span class='product-detail_number-item' onclick='incrementProductQuantity()'>+</span>" +
                                "</div>" +
                                "<button class=\"btn btn-outline-success search-bar-submit product-detail_btn\" type=\"submit\" " + ">ADD TO CART</button>" +
                            "</form>" +
                        "</div>" +
                        "<hr class='mt-5' />" +
                        "<div class='mt-5 product-detail_inform'>" +
                            "<ul class='product-detail_list'>" +
                                "<li><b class='d-inline-block'>Availability</b> <span class='d-inline-block'>In Stock</span></li>" +
                                "<li><b class='d-inline-block'>Shipping</b> <span class='d-inline-block'>01 day shipping. <samp class='cost-color'>Free pickup today</samp></span></li>" +
                                "<li><b class='d-inline-block'>Weight</b> <span class='d-inline-block'>0.5 kg</span></li>" +
                            "</ul>" +
                        "</div>" +
                    "</div>" +
                "</div>" +
                "<div class='row product-infor-wrapper'>" +
                    "<div class='col-4'><hr /></div>" +
                    "<div class='col product-infor d-flex'>" +
                        "<span class='product-infor-item active' onclick='createProductInformation(0, \""+ product.description +"\"); inforChoose(0)'>Description</span>" +
                        "<span class='product-infor-item'  onclick='createProductInformation(1, \""+ product.description +"\"); inforChoose(1)'>Information</span>" +
                        "<span class='product-infor-item' onclick='inforChoose(2)'>Reviews</span>" +
                    "</div>" +
                    "<div class='col-4'><hr /></div>" +
                "</div>" +
                "<div class='product-infor-text mt-5'>"+ product.description +"</div>" +
                "<div class=\"text-center featured-product-title\" style='margin-top: 80px'>" +
                "            Related Product" +
                "</div>" +
                "<div class='feature-product-item-wrapper row'></div>"
            ;

            $('.product-detail-wrapper').html(productHTML);
            getProductByCategory(product.category.id, product.id);
        }
    });
}

function showMessage() {
    // const urlString = document.location.href;
    // const regex = /add=(\w+)/;
    // const match = urlString.match(regex);
    // const resultValue = match ? match[1] : null;
    const resultValue = document.querySelector("#message").value;
    if(resultValue === "success") {
        let html = "<div class=\"add-product text-center\">\n" +
            "        <i class=\"bi bi-check-lg add-product-icon\"></i>\n" +
            "        <p class=\"add-product-text\">Add product successfully</p>\n" +
            "    </div>";
        $('body').append(html);
        let addProductDiv = $('.add-product');
        setTimeout(() => {
            addProductDiv.remove();
        }, 4000);
    }
}

function incrementProductQuantity() {
    let quantity = document.querySelectorAll(".product-detail_number-item")[1];
    quantity.value = parseInt(quantity.value) + 1;
}

function decreaseProductQuantity() {
    let quantity = document.querySelectorAll(".product-detail_number-item")[1];
    if(quantity.value === "1") {
        quantity.value = 1;
    } else {
        quantity.value = parseInt(quantity.value) - 1;
    }
}

function createProductInformation(value, information) {
    if(value === 0 || value === 1) {
        $('.product-infor-text').html(information);
    }
}

function inforChoose(val) {
    $('.product-infor-item').removeClass('active');
    $('.product-infor-item').eq(val).addClass('active');
}

function getProductByCategory(categoryID, productId) {
    $.ajax({
       type: "GET",
        url: "/shop/getProductByCategory?categoryId=" + categoryID + "&productId=" + productId,
        success: function (products) {
            var productHTML = "";
            products.forEach(function (product) {
                productHTML += "<div class=\"feature-product-item col-3 gt-5\">";
                productHTML += "<img src='/getProductImage/" + product.id + "' alt='' class='feature-product-item-img'>";
                productHTML += "<div class=\"feature-product-item-text\">";
                productHTML += "<a class=\"feature-product-item-title text-center d-block\" href='/shop/product/"+ product.id +"'>" + product.name + "</a>";
                productHTML += "<h5 class=\"feature-product-item-cost text-center\">" + "$" + product.cost + ".00</h5>";
                productHTML += "</div>";
                productHTML += "</div>";
            });
            $('.feature-product-item-wrapper').html(productHTML);
            console.log(products);
        }
    });
}