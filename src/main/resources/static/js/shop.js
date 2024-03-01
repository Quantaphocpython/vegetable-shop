// ready function
$(document).ready(function(){
    getProductSaleOffList("ALL");
    slideOne();
    slideTwo();
    rangeKeyUp();
    getAllProduct(0, 1000, "0", "ALL");
    showPages(0, 1000, "ALL");
    getLatestProduct();
});

function getAllProduct(min, max, pageNumber, categoryName, sort) {
    $.ajax({
        type: "GET",
        url: "/shop/getProductList/" + pageNumber + "?min=" + min + "&max=" + max + "&categoryName=" +categoryName +"&sort=" + sort ,
        success: function (data) {
            var productHTML = "";
            data.forEach(function (product) {
                productHTML += "<div class=\"feature-product-item col-4 gt-5\">";
                productHTML += "<img src='/getProductImage/" + product.id + "' alt='' class='feature-product-item-img'>";
                productHTML += "<div class=\"feature-product-item-text\">";
                productHTML += "<a class=\"feature-product-item-title text-center d-block\">" + product.name + "</a>";
                productHTML += "<h5 class=\"feature-product-item-cost text-center\">" + "$" + product.cost + ".00</h5>";
                productHTML += "</div>";
                productHTML += "</div>";
            });
            $('.shop-product').html(productHTML);
        }
    });
    $.ajax({
        type: "GET",
        url: "/shop/getProductSize?min=" + min + "&max=" + max + "&categoryName=" + categoryName,
        success: function (data) {
            $('.product-size').html(data);
        }
    });
}

function reset() {
    sliderOne.value = 0;
    sliderTwo.value = 1000;
    slideTwo();
    slideOne();
    const selectElement = document.querySelector('.form-select');
    selectElement.selectedIndex = 0;
    findByCostBetweenAndSort();
}

function findByCostBetweenAndSort(sort) {
    var min = document.getElementById("range1").value;
    var max = document.getElementById("range2").value;
    var categoryName = document.querySelector(".category-list-item-link.active").textContent.trim();
    getAllProduct(min, max, 0, categoryName, sort);
    showPages(min, max, categoryName);
}

function showPages(min, max, categoryName) {
    $.ajax({
        type: "GET",
        url: "/shop/getTotalPages?min=" + min + "&max=" + max + "&categoryName=" + categoryName,
        success: function (data) {
            var pageHTML = "";
            for(var i = 1; i <= data; i++) {
                if(i == 1)
                    pageHTML += "<li class=\"page-item active product-page\" aria-current=\"page\">";
                else
                    pageHTML += "<li class=\"page-item product-page\" aria-current=\"page\">";
                pageHTML += "<a class=\"page-link\" " +
                    "onclick='getAllProduct("+ (min) + ", " + (max) + ", " + (i - 1) + ", \"" + categoryName + "\");" +
                    "pageChoose(" + (i - 1)+ ")'>" + i + "</a>";
                pageHTML += "</li>";
            }
            $('.pagination.pagination-sm').html(pageHTML);
        }
    });
}

function pageChoose(val) {
    $('.pagination.pagination-sm .page-item').removeClass('active'); // Xóa class active ở tất cả các nút paging
    var newElement = $('.pagination.pagination-sm .product-page').eq(val).addClass('active');
}

function categoryChoose(val) {
    $('.category-list-item-link').removeClass('active'); // Xóa class active ở tất cả các nút paging
    $('.category-list-item').eq(val).find('.category-list-item-link').addClass('active');
}

function formatNumberWithDecimal(value, decimalPlaces) {
    if (typeof value !== 'number') {
        throw new Error('Giá trị đầu vào không phải là một số.');
    }

    return value.toFixed(decimalPlaces);
}

function getLatestProduct() {
    $.ajax({
        type: "GET",
        url: "/shop/getLatestProduct",
        success: function (data) {
            var productHTML = "";
            var i = 1;
            data.forEach(function (product, index) {
                productHTML += "<div class=\"row mt-3 \">" +
                    "                        <div class=\"col-6\">" +
                    "                            <img src=\"/getProductImage/"+ product.id +"\" alt=\"\" class=\"w-100 latest-product-img\">" +
                    "                        </div>" +
                    "                        <div class=\"col-6\">" +
                    "                            <a class=\"feature-product-item-title text-center d-block\">"+ product.name +"</a>" +
                    "                            <h5 class=\"feature-product-item-cost text-center \">"+
                                                (product.isSale == true ? formatNumberWithDecimal(product.saleCost, 2) :
                                                    formatNumberWithDecimal(product.cost, 2)) +"</h5>" +
                    "                        </div>" +
                    "                    </div>";
                if(index  === 2 || index === 5) {
                    $('.latest-product-row_' + i ).html(productHTML);
                    productHTML = "";
                    i++;
                }
            });
            $('.owl-carousel.latest-product-wrapper').owlCarousel({
                loop: true,
                margin: 10,
                nav: false,
                dots: true,
                autoplay: true,
                autoplayTimeout:4000,
                smartSpeed: 1200,
                autoplayHoverPause:true,
                time: 2000,
                items: 1
            });
        }
    });
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

// range slider

let sliderOne = document.getElementById("slider-1");
let sliderTwo = document.getElementById("slider-2");
let displayValOne = document.getElementById("range1");
let displayValTwo = document.getElementById("range2");
let minGap = 0;
let sliderTrack = document.querySelector(".slider-track");
let sliderMaxValue = document.getElementById("slider-1").max;
function slideOne(){
    if(parseInt(sliderTwo.value) - parseInt(sliderOne.value) <= minGap){
        sliderOne.value = parseInt(sliderTwo.value) - minGap;
    }
    displayValOne.value = sliderOne.value;
    fillColor();
}
function slideTwo(){
    if(parseInt(sliderTwo.value) - parseInt(sliderOne.value) <= minGap){
        sliderTwo.value = parseInt(sliderOne.value) + minGap;
    }
    displayValTwo.value = sliderTwo.value;
    fillColor();
}
function fillColor(){
    percent1 = (sliderOne.value / sliderMaxValue) * 100;
    percent2 = (sliderTwo.value / sliderMaxValue) * 100;
    sliderTrack.style.background = `linear-gradient(to right, #dadae5 ${percent1}% , #DD2222 ${percent1}% , #DD2222 ${percent2}%, #dadae5 ${percent2}%)`;
}

function rangeKeyUp() {
    if(parseInt(displayValOne.value) > parseInt(displayValTwo.value)) {
        displayValOne.value = parseInt(parseInt(displayValOne.value) / 10);
    }
    if(parseInt(displayValOne.value) > parseInt(displayValTwo.value)) {
        displayValOne.value = parseInt(parseInt(displayValOne.value) / 10);
    }
    if(displayValTwo.value > 1000)
        displayValTwo.value = 1000;
    sliderOne.value = displayValOne.value;
    sliderTwo.value = displayValTwo.value;
    fillColor();
}



