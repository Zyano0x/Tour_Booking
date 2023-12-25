let articleId = getParamId();
let article;
let articleReview;
const userId = document.head.dataset.userid;
const ratingOption = document.querySelector("#quality_review");
const reviewContent = document.querySelector("#review_text");

async function renderArticleHeader() {
    const parallaxWindow = document.querySelector(".parallax-window");
    const greyLayout = document.querySelector(".grey-layout");
    const name = document.getElementById("name");
    const author = document.querySelector("#author p span");
    const dateOfPosting = document.querySelector("#date-of-posting p span");
    const navigationName = document.querySelector(".navigation-name");

    parallaxWindow ? parallaxWindow.style.background = `url(${article.thumbnail}) no-repeat center/cover` : '';
    greyLayout ? greyLayout.style.height = "500px" : '';
    name ? name.textContent = article.title : '';
    author ? author.textContent = article.user.name : '';
    dateOfPosting ? dateOfPosting.textContent = dateFormatConvert1(article.dateOfPosting) : '';
    navigationName ? navigationName.textContent = articleId : '';

    const rating = await renderRating(`/api/article/${articleId}/article-reviews`);
    document.querySelectorAll(".genarateRating").forEach(element => {
        element.innerHTML = rating;
    });
}

/* END RENDER HEADER */

////////////////////////////////////////

/* RENDER CONTENT */
function renderContent(tourInfo, fatherBlockSelector) {
    try {
        const fatherBlock = document.querySelector(fatherBlockSelector);
        if (fatherBlock) {
            fatherBlock.innerHTML += tourInfo;
        } else {
            throw new Error(`>>> Element with selector '${fatherBlockSelector}' not found in the DOM`);
        }
    } catch (error) {
        console.error(">>> Error: " + error.message);
    }
}
/* END RENDER CONTENT */

////////////////////////////////////////

/*PAGINATION USER REVIEWS*/
let userReviews = [];
let currentPage = 1;
let perPage = 1;
let totalPage = 0;

function renderReviewOption() {
    try {
        const reviewOptionBlock = document.querySelector("#review_option");
        const submitOptionBlock = document.querySelector("#submit_option");
        if (reviewOptionBlock && submitOptionBlock) {
            let reviewOption;
            let submitOption;
            articleReview = userReviews.find(function (obj) {
                return obj.user.id == userId; // Thay id bằng thuộc tính cụ thể bạn muốn so sánh
            });
            if (articleReview !== undefined) {
                reviewOption = `<a
                href="javascript:void(0);"
                class="btn_1 add_bottom_30 .none-padding-right"
                data-toggle="modal"
                data-target="#myReview"
                >Chỉnh sửa</a>`;

                submitOption = `<button
                    type="submit"
                    class="btn_1 outline-none"
                    id="submit_update_review" 
                >Cập nhật</button>
                <button
                    type="submit"
                    class="btn_1 outline-none"
                    id="submit_delete_review"
                >Xóa</button>`;

                ratingOption ? ratingOption.value = articleReview.vote : '';
                reviewContent ? reviewContent.value = articleReview.content : '';
            } else {
                reviewOption = `<a
                href="javascript:void(0);"
                class="btn_1 add_bottom_30 .none-padding-right"
                data-toggle="modal"
                data-target="#myReview"
                >Thêm đánh giá</a>`;

                submitOption = `<button
                    type="submit"
                    class="btn_1 outline-none"
                    id="submit_review"
                >Gửi đánh giá</button>`;

                ratingOption ? ratingOption.value = '' : '';
                reviewContent ? reviewContent.value = '' : '';
            }
            reviewOptionBlock.innerHTML = reviewOption;
            submitOptionBlock.innerHTML = submitOption;
            handleCRUDReview();
        }
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}

function renderUserRating(score) {
    try {
        let rating = ``;
        if (score === 1) {
            rating = `<i class="icon-smile voted"></i><i class="icon-smile"></i><i class="icon-smile"></i><i class="icon-smile"></i><i class="icon-smile"></i>`;
        } else {
            for (let i = 0; i < 5; i++) {
                if (score > 0) {
                    rating += `<i class="icon-smile voted"></i>`;
                    score--;
                } else {
                    rating += `<i class="icon-smile"></i>`;
                }
            }
        }
        return rating;
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}

async function renderUsersReviews(userReviews) {
    try {
        const boxReview = document.querySelector("#boxReview");
        if (boxReview) {
            var htmls = '';
            for (const articleReview of userReviews) {
                htmls += `<hr />
                        <div class="review_strip_single">
                        <img
                            src="/assets/user/images/avatar3.jpg"
                            alt="Image"
                            class="rounded-circle"
                        />
                        <small> - ${articleReview.editDate ? "Chỉnh sửa - " + dateFormatConvert1(articleReview.editDate) : dateFormatConvert1(articleReview.dateOfPosting)} -</small>
                        <h4>${articleReview.user ? articleReview.user.name : ''}</h4>
                        <p>
                            ${articleReview.content}
                        </p>
                        <div class="rating userRating">
                            ${renderUserRating(parseInt(articleReview.vote))}
                        </div>
                        </div>
                        <!-- End review strip -->
                        `;
            }
            if (htmls != '')
                boxReview.innerHTML = htmls;
        } else {
            throw new Error(`>>> Element with selector '#boxReview' not found in the DOM`);
        }
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}

async function handleRenderUserReviews() {
    try {
        userReviews = await getApi(`/api/article/${articleId}/article-reviews`, "NotCallBack");
        renderReviewOption();

        // render page number
        totalPage = Math.ceil(userReviews.length / perPage);

        const pagination = document.querySelector("ul.pagination");
        if (totalPage > 1) {
            let htmls = ``;
            if (pagination) {
                htmls += `
                <li class="page-item page-item-previous" style="pointer-events: none;">
                    <span class="page-link" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                        <span class="sr-only">Previous</span>
                    </span>
                </li>
                `;
                for (var i = 1; i <= totalPage; i++) {
                    htmls += `
                    <li class="page-item page-item-number page-item-number-${i} ${i === 1 ? "active" : ''}" data-number="${i}"><span class="page-link">${i}</span></li>
                    `;
                }
                htmls += `
                <li class="page-item page-item-next">
                    <span class="page-link" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                        <span class="sr-only">Next</span>
                    </span>
                </li>
                `;
                pagination.innerHTML = htmls;
            } else
                throw new Error(`>>> Element with selector 'ul.pagination' not found in the DOM`);

            // Logic change page
            const pageItemsNumber = document.querySelectorAll("li.page-item-number");
            const pageItemPrevious = document.querySelector(".page-item-previous");
            const pageItemNext = document.querySelector(".page-item-next");

            pageItemsNumber.forEach(function (element) {
                element.addEventListener("click", function () {
                    pageItemsNumber.forEach(function (item) {
                        item.classList.remove("active");
                    });
                    currentPage = parseInt(element.dataset.number);
                    if (currentPage > 1) {
                        pageItemPrevious.removeAttribute("style");
                    } else {
                        pageItemPrevious.style.pointerEvents = "none";
                    }

                    if (currentPage < totalPage) {
                        pageItemNext.removeAttribute("style");
                    } else {
                        pageItemNext.style.pointerEvents = "none";
                    }
                    element.classList.add("active");
                    renderUsersReviews(userReviews.slice(
                        (currentPage - 1) * perPage, (currentPage - 1) * perPage + perPage
                    ));
                })
            });

            pageItemPrevious.addEventListener("click", function () {
                pageItemsNumber.forEach(function (item) {
                    item.classList.remove("active");
                });
                pageItemNext.removeAttribute("style");
                currentPage--;
                if (currentPage === 1) {
                    this.setAttribute("style", "pointer-events: none;");
                }
                document.querySelector(`.page-item-number-${currentPage}`).classList.add("active");
                renderUsersReviews(userReviews.slice(
                    (currentPage - 1) * perPage, (currentPage - 1) * perPage + perPage
                ));
            });

            pageItemNext.addEventListener("click", function () {
                pageItemsNumber.forEach(function (item) {
                    item.classList.remove("active");
                });
                pageItemPrevious.removeAttribute("style");
                currentPage++;
                if (currentPage === totalPage) {
                    this.setAttribute("style", "pointer-events: none;");
                }
                document.querySelector(`.page-item-number-${currentPage}`).classList.add("active");
                renderUsersReviews(userReviews.slice(
                    (currentPage - 1) * perPage, (currentPage - 1) * perPage + perPage
                ));
            });
        } else {
            pagination.innerHTML = '';
        }
        renderUsersReviews(userReviews.slice(
            (currentPage - 1) * perPage, (currentPage - 1) * perPage + perPage
        ));
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}
/*END PAGINATION USER REVIEWS*/

////////////////////////////////////////

/* CRUD ARTICLE REVIEWS */
const closeModalReview = document.getElementById("close-modal-review");

function containsUnwantedWords(comment) {
    comment = comment.toLowerCase();
    for (const unwantedKeyword of unwantedKeywords) {
        if (comment.includes(unwantedKeyword)) {
            return true; // Nếu tìm thấy từ khóa không mong muốn, trả về true
        }
    }
    return false; // Nếu không tìm thấy từ khóa không mong muốn, trả về false
}

function alertFunc(icon, color, bg, content) {
    let animatedAlert = document.getElementById("animatedAlert");
    if (animatedAlert) {
        animatedAlert.insertAdjacentHTML("afterbegin", `<div class="animated-alert-item"><i class="${icon}" style="color: ${color}"></i> ${content}</div>`)
        let animatedAlertItem = animatedAlert.firstChild;
        // let animatedAlertItem = document.getElementsByClassName("animated-alert-item");

        animatedAlertItem.setAttribute("style", `background: ${bg}`)
        // Hiển thị thông báo
        animatedAlertItem.classList.add("show");
        animatedAlertItem.style.animation = "fadeIn 1s ease";

        // Đặt thời gian để di chuyển và biến mất sau 3 giây
        setTimeout(function () {
            animatedAlertItem.classList.remove("show");
            animatedAlertItem.style.animation = "slideOut 1s ease";

            // // Đặt thời gian để ẩn thông báo sau khi kết thúc animation
            setTimeout(function () {
                animatedAlertItem.style.animation = "";
                animatedAlertItem.parentNode.removeChild(animatedAlertItem);
            }, 2000);
        }, 3000);
    }
}

function addNewReview(rating, review) {
    try {
        let myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");

        let raw = JSON.stringify({
            "content": review,
            "vote": rating,
            "userId": userId,
            "articleId": articleId,
        });

        let requestOptions = {
            method: 'POST', headers: myHeaders, body: raw, redirect: 'follow'
        };

        // Make the API request
        fetch("/api/article-review", requestOptions)
            .then(response => {
                if (response.ok) {
                    return response.text();
                } else {
                    throw new Error('Failed to add review. Status: ' + response.status);
                }
            })
            .then(result => {
                alertFunc("fa-solid fa-circle-check", "#5cc41a", "#dafbb9", "Thêm đánh giá thành công!");
                renderArticleHeader();
                handleRenderUserReviews();
                closeModalReview.click();
            })
            .catch(error => {
                console.log(">>> Error: " + error.message);
            });
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}

function updateReview(rating, review) {
    try {
        let myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");

        let raw = JSON.stringify({
            "content": review,
            "vote": rating,
            "userId": userId,
            "articleId": articleId,
        });

        let requestOptions = {
            method: 'PUT', headers: myHeaders, body: raw, redirect: 'follow'
        };

        // Make the API request
        fetch(`/api/update-article-review/${articleReview.id}`, requestOptions)
            .then(response => {
                if (response.ok) {
                    return response.text();
                } else {
                    throw new Error('Failed to update review. Status: ' + response.status);
                }
            })
            .then(result => {
                renderReviewOption();

                alertFunc("fa-solid fa-circle-check", "#5cc41a", "#dafbb9", "Cập nhật đánh giá thành công!");
                renderArticleHeader();
                currentPage = 1;
                handleRenderUserReviews();
                closeModalReview.click();
            })
            .catch(error => {
                console.log(">>> Error: " + error.message);
            });
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}

async function deleteReview() {
    try {
        const response = await fetch(`/api/delete-article-review/${articleReview.id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json', // Nếu API yêu cầu header có kiểu dữ liệu
                // 'Authorization': 'Bearer YOUR_ACCESS_TOKEN', // Nếu có yêu cầu xác thực
            },
            // body: JSON.stringify({}) // Nếu có dữ liệu gửi kèm (thường không cần cho DELETE)
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        renderReviewOption();

        alertFunc("fa-solid fa-circle-check", "#5cc41a", "#dafbb9", "Xóa đánh giá thành công!");
        renderArticleHeader();
        currentPage = 1;
        handleRenderUserReviews();
        closeModalReview.click();
    } catch (error) {
        console.error('>>> Error deleting review:', error.message);
    }
}

function handleCRUDReview() {
    try {
        const modal = document.querySelector("#myReview");
        if (modal) {
            // ADD NEW
            const submitReviewBtn = document.querySelector("#submit_review");
            if (submitReviewBtn) {
                submitReviewBtn.addEventListener("click", function () {
                    ratingOption && ratingOption.value != '' ? reviewContent && reviewContent.value != '' ? containsUnwantedWords(reviewContent.value) ? alertFunc("fa-solid fa-circle-exclamation", "#faad14", "#fbf1be", "Bình luận chứa ngôn từ không phù hợp!") :
                        addNewReview(ratingOption.value, reviewContent.value, modal) : alertFunc("fa-solid fa-circle-exclamation", "#faad14", "#fbf1be", "Vui lòng đưa ra nhận xét!")
                        : alertFunc("fa-solid fa-circle-exclamation", "#faad14", "#fbf1be", "Vui lòng đưa ra điểm đánh giá!");
                });
            }

            // UPDATE
            const submitUpdateReviewBtn = document.querySelector("#submit_update_review");
            if (submitUpdateReviewBtn) {
                submitUpdateReviewBtn.addEventListener("click", function () {
                    ratingOption && ratingOption.value != '' ? reviewContent && reviewContent.value != '' ?
                        ratingOption.value == articleReview.vote && reviewContent.value == articleReview.content ? alertFunc("fa-solid fa-circle-exclamation", "#faad14", "#fbf1be", "Nội dung đánh giá không có thay đổi!") : containsUnwantedWords(reviewContent.value) ? alertFunc("fa-solid fa-circle-exclamation", "#faad14", "#fbf1be", "Bình luận có từ ngữ không phù hợp!") : updateReview(ratingOption.value, reviewContent.value, modal) : alertFunc("fa-solid fa-circle-exclamation", "#faad14", "#fbf1be", "Vui lòng đưa ra nhận xét!")
                        : alertFunc("fa-solid fa-circle-exclamation", "#faad14", "#fbf1be", "Vui lòng đưa ra điểm đánh giá!");
                });
            }

            // DELETE
            const submitDeleteReviewBtn = document.querySelector("#submit_delete_review");
            if (submitDeleteReviewBtn) {
                submitDeleteReviewBtn.addEventListener("click", function () {
                    deleteReview(modal);
                });
            }
        }
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}
/* END CRUD ARTICLE REVIEWS */

////////////////////////////////////////

function getParamId() {
    try {
        // Lấy URL hiện tại
        var currentURL = window.location.href;

        // Tạo đối tượng URL từ URL hiện tại
        var urlObject = new URL(currentURL);

        // Lấy giá trị của tham số có tên là "id"
        return urlObject.pathname.split('/').pop();
    } catch (error) {
        console.log(">>> Error when getting url parameter: " + error.message);
    }
}

import { getApi, dateFormatConvert1, unwantedKeywords, renderRating } from './global_function.js';

async function start() {
    try {
        article = await getApi(`/api/articles?id=${articleId}`, "NotCallBack");
        if (article !== undefined) {
            renderArticleHeader();
            renderContent(article.content, "#article-content");
            handleRenderUserReviews();
        }
    } catch (error) {
        console.error(">>> Error:", error.message);
    }
}

document.addEventListener("DOMContentLoaded", function () {
    start();
})