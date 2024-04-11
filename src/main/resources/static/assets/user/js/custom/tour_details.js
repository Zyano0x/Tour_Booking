let tourId = getParamId();
let tour;
let tourReview;
const userId = document.head.dataset.userid;
const ratingOption = document.querySelector("#quality_review");
const reviewContent = document.querySelector("#review_text");

async function renderTourHeader() {
    const parallaxWindow = document.querySelector(".parallax-window");
    const greyLayout = document.querySelector(".grey-layout");
    const name = document.getElementById("name");
    const destination = document.querySelector(".destination");
    const typeOfTour = document.querySelector(".type-of-tour");
    const priceForAdult = document.querySelector("#price_single_main span");
    const priceForChildren = document.querySelector("#price_single_main_for_children span");
    const navigationName = document.querySelector(".navigation-name");

    parallaxWindow ? parallaxWindow.style.background = `url(${tour.thumbnail}) no-repeat center/cover` : '';
    greyLayout ? greyLayout.style.height = "500px" : '';
    name ? name.textContent = tour.name : '';
    destination ? destination.textContent = tour.destination.name : '';
    typeOfTour ? typeOfTour.textContent = tour.typeOfTour.name : '';
    priceForAdult ? priceForAdult.textContent = moneyFormat(tour.priceForAdult, true) : '';
    priceForChildren ? priceForChildren.textContent = moneyFormat(tour.priceForChildren, true) : '';
    navigationName ? navigationName.textContent = tour.name : '';

    const rating = await renderRating(`/api/tour/${tourId}/tour-reviews`);
    document.querySelectorAll(".genarateRating").forEach(element => {
        element.innerHTML = rating;
    });
}

/*TOUR IMAGES*/
function renderTourImages(tourImages) {
    try {
        const slide = document.querySelector(".sp-slides");
        const thumbnails = document.querySelector(".sp-thumbnails");

        if (!slide || !thumbnails) {
            throw new Error("Elements with id '#slide or #thumbnails' not found in the DOM");
        }

        if (tourImages.length > 0) {
            const slideHtmls = tourImages.map(tourImage => `
                <div class="sp-slide">
                    <img
                        class="sp-image"
                        src="${tourImage.path}"
                        data-src="${tourImage.path}"
                        data-small="${tourImage.path}"
                        data-medium="${tourImage.path}"
                        data-large="${tourImage.path}"
                        data-retina="${tourImage.path}"
                    />
                </div>`
            ).join('');

            const thumbnailsHtmls = tourImages.map(tourImage => `
                <img
                    alt="Image"
                    class="sp-thumbnail"
                    src="${tourImage.path}"
                />`
            ).join('');

            if (slideHtmls) {
                slide.insertAdjacentHTML('beforeend', slideHtmls);
            }

            if (thumbnailsHtmls) {
                thumbnails.insertAdjacentHTML('beforeend', thumbnailsHtmls);
            }
        }
    } catch (error) {
        console.error(">>> Error: " + error.message);
    }
}

function loadScript(src) {
    return new Promise(function (resolve, reject) {
        var script = document.createElement("script");
        script.src = src;
        script.onload = resolve;
        script.onerror = reject;
        document.body.insertAdjacentElement("beforeend", script);
    });
}

async function handleGetTourImages() {
    await getApi(`/api/tour/${tourId}/tour-images`, renderTourImages);

    // Create layout & animation slider
    await loadScript("/assets/user/js/jquery.sliderPro.min.js")
        .then(function () {
            // Mã sliderPro sẽ được thực hiện khi script đã được tải xong.
            $("#Img_carousel").sliderPro({
                width: 960,
                height: 500,
                fade: true,
                arrows: true,
                buttons: false,
                fullScreen: false,
                smallSize: 500,
                startSlide: 0,
                mediumSize: 1000,
                largeSize: 3000,
                thumbnailArrows: true,
                autoplay: false,
            });
        })
        .catch(function (error) {
            console.error(">>> Error loading script:", error);
        });
}
/*END TOUR IMAGES*/

////////////////////////////////////////

/*TOUR INFO {description, schedule, service}*/
function renderInfo(tourInfo, fatherBlockSelector) {
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
/*END TOUR INFO {description, schedule, service}*/

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
            tourReview = userReviews.find(function (obj) {
                return obj.user.id == userId; // Thay id bằng thuộc tính cụ thể bạn muốn so sánh
            });
            if (tourReview !== undefined) {
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

                ratingOption ? ratingOption.value = tourReview.vote : '';
                reviewContent ? reviewContent.value = tourReview.content : '';
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
            for (const tourReview of userReviews) {
                htmls += `<hr />
                        <div class="review_strip_single">
                            <img
                                src="/assets/user/images/avatar3.jpg"
                                alt="Image"
                                class="rounded-circle"
                            />
                            <small> - ${tourReview.editDate ? "Chỉnh sửa - " + dateFormatConvert1(tourReview.editDate) : dateFormatConvert1(tourReview.dateOfPosting)} -</small>
                            <h4>${tourReview.user ? tourReview.user.name : ''}</h4>
                            <p>
                                ${tourReview.content}
                            </p>
                            <div class="review-footer" data-reviewId="${tourReview.id}">
                                <div class="rating userRating">
                                    ${renderUserRating(parseInt(tourReview.vote))}
                                </div>
                                ${(await getApi(`/api/user-by-id?id=${userId}`)).role == "ADMIN" ? `<span>Xóa</span>` : ''}
                            </div> 
                        </div>
                        <!-- End review strip -->
                        `;
            }

            boxReview.innerHTML = htmls;

            // DELETE BY ADMIN
            const deleteReviewsByAdminBtn = document.querySelectorAll(".review-footer span");
            if (deleteReviewsByAdminBtn.length > 0) {
                for (const item of deleteReviewsByAdminBtn) {
                    item.addEventListener("click", function (e) {
                        deleteReview(item.parentNode.dataset.reviewid);
                    });
                }
            }
        } else {
            throw new Error(`>>> Element with selector '#boxReview' not found in the DOM`);
        }
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}

async function handleRenderUserReviews() {
    try {
        userReviews = await getApi(`/api/tour/${tourId}/tour-reviews`, "NotCallBack");
        userReviews.reverse();
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

/*BOOKING*/
let deparuteDayIdForUpdate;

function updateBookingQuantity() {
    try {
        const remainingQuanityBlock = document.querySelector("#remainingQuanity");
        if (remainingQuanityBlock) {
            setInterval(async function () {
                remainingQuanityBlock.innerText = (await getApi(`/api/departure-day?id=${deparuteDayIdForUpdate}`, "NotCallBack")).quantity;
            }, 100);
        } else {
            throw new Error(`>>> Element with selector '#remainingQuanity' not found in the DOM`);
        }
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}

async function bookingOverLay() {
    try {
        var departureDays = await getApi(`/api/tour/${tourId}/departure-days`, "NotCallBack");
        var validDepartureDays = departureDays.filter(departureDay => departureDay.status && compareDateNow(departureDay.departureDay) == 1 && departureDay.quantity > 0);

        if (validDepartureDays.length <= 0) {
            document.querySelector("#booking_box").style.pointerEvents = "none";
            document.querySelector(".booking_overlay").style.display = "flex";
            return true;
        }
        return false;
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}

function changeDepartureDay() {
    try {
        var selectedValueInputs = document.querySelectorAll(".dd-option");

        if (selectedValueInputs.length > 0) {
            const departureTimeBlock = document.querySelector("#departureTime");
            selectedValueInputs.forEach(function (option) {
                option.addEventListener('click', async function () {
                    const optionValue = document.querySelector(".dd-option-selected .dd-option-value");


                    // Cập nhật thời gian khởi hành tương ứng cho từng ngày khởi hành
                    departureTimeBlock ? departureTimeBlock.textContent = (await getApi(`/api/departure-day?id=${optionValue.value}`)).departureTime : '';
                    if (optionValue) {
                        deparuteDayIdForUpdate = parseInt(optionValue.value);
                    } else {
                        console.log("Not found element with selector '.dd-option-selected .dd-option-value' in DOM");
                    }
                });
            });
        } else {
            console.log("Not found element with selector '.dd-option' in DOM");
        }
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}

function booking() {
    try {
        const bookingBtn = document.querySelector("#booking_btn");
        if (bookingBtn) {
            const totalBlock = document.getElementById("total");
            const adult = document.getElementById("adults");
            const children = document.getElementById("children");
            const departureDay = document.querySelector(".dd-option-selected .dd-option-value");
            const remainingQuanityBlock = document.querySelector("#remainingQuanity");

            bookingBtn.addEventListener("click", function () {
                let totalPrice = moneyFormat(totalBlock.innerText);
                if (adult.value != '0') {
                    let totalQuantity = parseInt(adult.value) + parseInt(children.value);
                    if (parseInt(remainingQuanityBlock.innerText) >= totalQuantity) {
                        const payment = {
                            "userId": userId,
                            "total": totalPrice,
                            "quantityOfAdult": adult.value,
                            "quantityOfChild": children.value,
                            "departureDayId": departureDay.value
                        };

                        fetch('/api/payment/create', {
                            method: "POST",
                            headers: {
                                "Content-Type": "application/json",
                            },
                            body: JSON.stringify(payment),
                        })
                            .then((response) => response.json())
                            .then((data) => {
                                console.log(data.paymentUrl);
                                window.location.href = data.paymentUrl;
                            })
                            .catch((error) => {
                                console.error("Error creating payment:", error);
                            });
                    } else if (parseInt(remainingQuanityBlock.innerText) === 0) {
                        alertFunc("fa-solid fa-circle-exclamation", "#faad14", "#fbf1be", "Ngày khởi hành bạn chọn đã hết vé!");
                    } else {
                        alertFunc("fa-solid fa-circle-exclamation", "#faad14", "#fbf1be", "Số lượng vé còn lại không đủ!");
                    }
                } else {
                    alertFunc("fa-solid fa-circle-exclamation", "#faad14", "#fbf1be", "Số lượng vé cho người lớn phải lớn hơn 0!");
                }
            })
        } else {
            console.log("Not found element width selector '#bookingBtn' in DOM");
        }
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}

async function handleBooking() {
    try {
        if (!(await bookingOverLay(tourId))) {
            await getDropList(`/api/tour/${tourId}/departure-days`, renderDepartureDropList, "#departureDays", "all_tours.png");

            // Set departureDayId ban đầu
            deparuteDayIdForUpdate = parseInt(document.querySelector(".dd-option-selected .dd-option-value").value);

            // Hiển thị thời gian
            const timeBlock = document.querySelector("#time");
            timeBlock ? timeBlock.textContent = tour.time : '';

            // Hiển thị giờ khởi hành
            getDepartureTime("#departureTime", deparuteDayIdForUpdate);

            // Chạy cập nhật
            updateBookingQuantity();

            changeDepartureDay();

            changeQuantity(tour.priceForAdult, tour.priceForChildren);

            booking();
        } else {
            document.querySelector("#departureDays").innerHTML += `<option
                value=""
                data-imagesrc="/assets/user/images/icons_search/all_tours.png">
                ...
            </option>`;
            ddslick("#departureDays");
        }
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}
/*END BOOKING*/

////////////////////////////////////////

/*CRUD TOUR REVIEW */
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

function addNewReview(rating, review) {
    try {
        let myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");

        let raw = JSON.stringify({
            "content": review,
            "vote": rating,
            "userId": userId,
            "tourId": tourId,
        });

        let requestOptions = {
            method: 'POST', headers: myHeaders, body: raw, redirect: 'follow'
        };

        // Make the API request
        fetch("/api/tour-review", requestOptions)
            .then(response => {
                if (response.ok) {
                    return response.text();
                } else {
                    throw new Error('Failed to add tour review. Status: ' + response.status);
                }
            })
            .then(result => {
                alertFunc("fa-solid fa-circle-check", "#5cc41a", "#dafbb9", "Thêm đánh giá thành công!");
                renderTourHeader();
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
            "tourId": tourId,
        });

        let requestOptions = {
            method: 'PUT', headers: myHeaders, body: raw, redirect: 'follow'
        };

        // Make the API request
        fetch(`/api/update-tour-review/${tourReview.id}`, requestOptions)
            .then(response => {
                if (response.ok) {
                    return response.text();
                } else {
                    throw new Error('Failed to update tour review. Status: ' + response.status);
                }
            })
            .then(result => {
                renderReviewOption();

                alertFunc("fa-solid fa-circle-check", "#5cc41a", "#dafbb9", "Cập nhật đánh giá thành công!");
                renderTourHeader();
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

async function deleteReview(id) {
    try {
        const response = await fetch(`/api/delete-tour-review/${id}`, {
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
        renderTourHeader();
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
                        addNewReview(ratingOption.value, reviewContent.value) : alertFunc("fa-solid fa-circle-exclamation", "#faad14", "#fbf1be", "Vui lòng đưa ra nhận xét!")
                        : alertFunc("fa-solid fa-circle-exclamation", "#faad14", "#fbf1be", "Vui lòng đưa ra điểm đánh giá!");
                });
            }

            // UPDATE
            const submitUpdateReviewBtn = document.querySelector("#submit_update_review");
            if (submitUpdateReviewBtn) {
                submitUpdateReviewBtn.addEventListener("click", function () {
                    ratingOption && ratingOption.value != '' ? reviewContent && reviewContent.value != '' ?
                        ratingOption.value == tourReview.vote && reviewContent.value == tourReview.content ? alertFunc("fa-solid fa-circle-exclamation", "#faad14", "#fbf1be", "Nội dung đánh giá không có thay đổi!") : containsUnwantedWords(reviewContent.value) ? alertFunc("fa-solid fa-circle-exclamation", "#faad14", "#fbf1be", "Bình luận có từ ngữ không phù hợp!") : updateReview(ratingOption.value, reviewContent.value) : alertFunc("fa-solid fa-circle-exclamation", "#faad14", "#fbf1be", "Vui lòng đưa ra nhận xét!")
                        : alertFunc("fa-solid fa-circle-exclamation", "#faad14", "#fbf1be", "Vui lòng đưa ra điểm đánh giá!");
                });
            }

            // DELETE
            const submitDeleteReviewBtn = document.querySelector("#submit_delete_review");
            if (submitDeleteReviewBtn) {
                submitDeleteReviewBtn.addEventListener("click", function () {
                    deleteReview(tourReview.id);
                });
            }
        }
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}
/*END CRUD TOUR REVIEW */

////////////////////////////////////////

/*MAP*/
function renderMAP(locationSRC) {
    const ggMapIframe = document.querySelector("#gg-map iframe");
    ggMapIframe && ggMapIframe.getAttribute("src") != locationSRC ? ggMapIframe.setAttribute("src", `${locationSRC}`) : '';
}

function handleGetMap() {
    // Chạy renderMap lần đầu tiên và hiển thị departurePoint của departureDay được chọn đầu tiên
}
/*END MAP*/

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

import { getDropList, renderRating, getApi, renderDepartureDropList, compareDateNow, ddslick, changeQuantity, dateFormatConvert1, unwantedKeywords, moneyFormat, alertFunc, getDepartureTime } from './global_function.js';

async function start() {
    try {
        tour = await getApi(`/api/tour?id=${tourId}`, "NotCallBack");
        if (tour !== undefined) {
            renderTourHeader();
            handleGetTourImages();
            handleBooking();
            renderInfo(tour.departurePoint, ".departure-point-content");
            renderInfo(tour.description, ".description-content");
            renderInfo(tour.schedule, ".schedule-content");
            renderInfo(tour.service, ".service-content");
            handleRenderUserReviews();
        }
    } catch (error) {
        console.error(">>> Error:", error.message);
    }
}

document.addEventListener("DOMContentLoaded", function () {
    start();
})
