
$(document).ready(function(){
    getAllBlog("ALL", 0);
    getRecentNews();
    showPages("ALL");
});

function getAllBlog(blogCategoryName, pageNumber) {
    $.ajax({
        type: "GET",
        url: "/blog/getBlogList?blogCategoryName=" + blogCategoryName + "&pageNumber=" + pageNumber,
        success: function (data) {
            var blogHTML = "";
            data.forEach(function (blog) {
                blogHTML +=
                    "                <div class=\"blog-item col-6 mb-5\" th:each=\"blog : ${blogs}\">" +
                    "                    <img src=\"/getBlogImage/"+ blog.id +"\" class=\"blog-item-image gt-5 w-100\">" +
                    "                    <div class=\"createDate mt-3 mb-3\">" +
                    "                        <i class=\"bi bi-calendar createDate-icon\"></i>" +
                    "                        <h5 class=\"createDate-time\">"+ blog.formattedDate +"</h5>" +
                    "                    </div>" +
                    "                    <a th:text=\"${blog.getTitle()}\" class=\"blog-item-title mb-3\" href=\"#\">"+ blog.title +"</a>" +
                    "                    <p class=\"blog-item-content mb-3\">"+ blog.content +"</p>" +
                    "                    <button class=\"blog-item-button mb-3\">"+ "READ MORE <i class=\"bi bi-arrow-right\"></i>" +"</button>" +
                    "                </div>"
            });
            $(".blog-wrapper").html(blogHTML);
        }
    })
}

function renderBlog(blogCategoryName) {
    getAllBlog(blogCategoryName, 0);
    showPages(blogCategoryName);
}

function showPages(blogCategoryName) {
    $.ajax({
        type: "GET",
        url: "/blog/getTotalPages?blogCategoryName=" + blogCategoryName,
        success: function (data) {
            var pageHTML = "";
            for(var i = 1; i <= data; i++) {
                if(i == 1)
                    pageHTML += "<li class=\"page-item active product-page\" aria-current=\"page\">";
                else
                    pageHTML += "<li class=\"page-item product-page\" aria-current=\"page\">";
                pageHTML += "<a class=\"page-link\" " +
                    "onclick='getAllBlog(\"" + blogCategoryName + "\", " + (i - 1) + ");" +
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

function getRecentNews() {
    $.ajax({
        type: "GET",
        url: "/blog/getRecentNews",
        success: function (data) {
            var blogHTML = "";
            data.forEach(function (blog) {
                blogHTML += "<a class=\"row recent_news-item\" href=\"/\">" +
                    "                        <div class=\"col-3\">" +
                    "                            <img src=\"home/getBlogImage/"+ blog.id +"\" alt=\"\" class=\"w-100 h-100 recent_news-img\">" +
                    "                        </div>" +
                    "                        <div class=\"col-9\">" +
                    "                            <h6 class=\"recent_news-title\">"+ blog.title +"</h6>" +
                    "                            <span class=\"recent_news-time\">"+ blog.formattedDate +"</span>" +
                    "                        </div>" +
                    "                    </a>"
            });
            $('.recent_news-wrapper').html(blogHTML);
        }
    })
}

function blogChoose(val) {
    $('.category-list-item-link').removeClass('active'); // Xóa class active ở tất cả các nút paging
    $('.category-list-item').eq(val).find('.category-list-item-link').addClass('active');
}



