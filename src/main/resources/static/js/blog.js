

$(document).ready(function(){
    loadBlogDetail();
    getRecentNews();
});

function loadBlogDetail() {
    const path = document.location.search;
    var match = path.match(/blogId=(\d+)/);
    var blogId = match ? match[1] : null;

    if(path.includes("blogId")) {
        blogId = parseInt(blogId);
        showBlogDetail(blogId);
        document.querySelector('.blog-wrapper').scrollIntoView({
            behavior: "smooth",
            block: "start"
        });
    }
    else {
        getAllBlog("ALL", 0);
        showPages("ALL");
    }
}

function handleKeyPress(event) {
    if (event.key === 'Enter') {
        renderSearchBlog();
    }
}

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
                    "                    <a th:text=\"${blog.getTitle()}\" class=\"blog-item-title mb-3\" onclick='showBlogDetail("+ blog.id +")'>"+ blog.title +"</a>" +
                    "                    <p class=\"blog-item-content mb-3\">"+ blog.content +"</p>" +
                    "                    <button class=\"blog-item-button mb-3\" onclick='showBlogDetail("+ blog.id +")'>"+ "READ MORE <i class=\"bi bi-arrow-right\"></i>" +"</button>" +
                    "                </div>"
            });
            $(".blog-wrapper").html(blogHTML);
        }
    })
}

function renderSearchBlog() {
    let blogTitle = document.querySelector('.blog-search-content');
    searchBlogByTitle(blogTitle.value, 0);
    getBlogSearchToTalPages(blogTitle.value);
    blogTitle.value = "";
}

function searchBlogByTitle(blogTitle, pageNumber) {
    $.ajax({
        type: "GET",
        url: "/blog/searchBlog?blogTitle=" + blogTitle + "&pageNumber=" + pageNumber,
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
                    "                    <a th:text=\"${blog.getTitle()}\" class=\"blog-item-title mb-3\" onclick='showBlogDetail("+ blog.id +")'>"+ blog.title +"</a>" +
                    "                    <p class=\"blog-item-content mb-3\">"+ blog.content +"</p>" +
                    "                    <button class=\"blog-item-button mb-3\" onclick='showBlogDetail("+ blog.id +")'>"+ "READ MORE <i class=\"bi bi-arrow-right\"></i>" +"</button>" +
                    "                </div>"
            });
            $(".blog-wrapper").html(blogHTML);
        }
    })
}

function getBlogSearchToTalPages(blogTitle) {
    $.ajax({
        type: "GET",
        url: "/blog/getBlogSearchToTalPages?blogTitle=" + blogTitle,
        success: function (data) {
            var pageHTML = "";
            for(var i = 1; i <= data; i++) {
                if(i == 1)
                    pageHTML += "<li class=\"page-item active product-page\" aria-current=\"page\">";
                else
                    pageHTML += "<li class=\"page-item product-page\" aria-current=\"page\">";
                pageHTML += "<a class=\"page-link\" " +
                    "onclick='searchBlogByTitle(\"" + blogTitle + "\", " + (i - 1) + ");" +
                    "pageChoose(" + (i - 1)+ ")'>" + i + "</a>";
                pageHTML += "</li>";
            }
            $('.pagination.pagination-sm').show().html(pageHTML);
        }
    });
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
            $('.pagination.pagination-sm').show().html(pageHTML);
        }
    });
}

function pageChoose(val) {
    $('.pagination.pagination-sm .page-item').removeClass('active'); // Xóa class active ở tất cả các nút paging
    var newElement = $('.pagination.pagination-sm .product-page').eq(val).addClass('active');
}

function showBlogDetail(blogId) {
    $.ajax({
        type: "GET",
        url: "/blog/findBlogById?blogId=" + blogId,
        success: function (blog) {
            let blogDetailHTML = "";
            blogDetailHTML += "<img src=\"/getBlogImage/"+ blog.id +"\" class=\"gt-5 w-100\">";
            blogDetailHTML += "<h4 class='blog_detail-title'>"+ blog.title +"</h4>";
            blogDetailHTML += "<p class='blog_detail-content'>"+ blog.content +"</p>";
            blogDetailHTML += "<div class='row align-items-center'>";
            blogDetailHTML +=
                "<div class='col-6 d-flex align-items-center'>" +
                    "<img src='/img/male.png' class='blog_detail-image' />" +
                    "<div>" +
                        "<h4 class='blog_detail-text'>"+ capitalizeFirstLetter(blog.user.firstName) + " " + capitalizeFirstLetter(blog.user.lastName) +"</h4>" +
                        "<span class='blog_detail-role'>Admin</span>" +
                    "</div>" +
                "</div>";
            blogDetailHTML +=
                "<div class='col-6 d-flex'>" +
                    "<h4 class='blog_detail-text' style='margin: 0; line-height: 20px'>Categories: </h4>" +
                    "<span style='line-height: 20px; margin-left: 5px;'>"+ blog.blogCategories.map(blogCategory => blogCategory.name.toUpperCase()).join(', ') +"</span>"
                "</div>";
            blogDetailHTML += "</div>";

            $(".blog-wrapper").html(blogDetailHTML);
            $('.pagination.pagination-sm').hide();
            showPostYouMayLike(blogId);
        }
    })
}

function showPostYouMayLike(blogId) {
    $.ajax({
        type: "GET",
        url: "/blog/getBlogByBlogCategories?blogId=" + blogId,
        success: function (data) {
            let blogHTML = "";
            blogHTML += "<div class=\"text-center featured-product-title mt-5\">Post You May Like</div>";
            data.forEach(function (blog) {
                blogHTML +=
                    "                <div class=\"blog-item col-4 mt-5\" th:each=\"blog : ${blogs}\">" +
                    "                    <img src=\"/getBlogImage/"+ blog.id +"\" class=\"blog-item-image gt-5 w-100\">" +
                    "                    <div class=\"createDate mt-3 mb-3\">" +
                    "                        <i class=\"bi bi-calendar createDate-icon\"></i>" +
                    "                        <h5 class=\"createDate-time\">"+ blog.formattedDate +"</h5>" +
                    "                    </div>" +
                    "                    <a th:text=\"${blog.getTitle()}\" class=\"blog-item-title mb-3\" onclick='showBlogDetail("+ blog.id +")'>"+ blog.title +"</a>" +
                    "                    <p class=\"blog-item-content mb-3\">"+ blog.content +"</p>" +
                    "                    <button class=\"blog-item-button mb-3\" onclick='showBlogDetail("+ blog.id +")'>"+ "READ MORE <i class=\"bi bi-arrow-right\"></i>" +"</button>" +
                    "                </div>"
            });
            $(".blog-post-like").html(blogHTML);
        }
    })
}

function capitalizeFirstLetter(string) {
    return string.replace(/\b\w/g, match => match.toUpperCase());
}

function getRecentNews() {
    $.ajax({
        type: "GET",
        url: "/blog/getRecentNews",
        success: function (data) {
            var blogHTML = "";
            data.forEach(function (blog) {
                blogHTML += "<a class=\"row recent_news-item\" onclick='showBlogDetail("+ blog.id +")'>" +
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



