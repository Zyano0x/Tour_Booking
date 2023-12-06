async function renderTourHeader(tour) {
    const name = document.querySelector("#tourName");
    if (name)
        name.innerText = tour.name;

    const caption = document.querySelector(".caption");
    if (caption)
        caption.innerText = tour.description;

    const rating = await renderToursRating(`${tour.id}`);
    document.querySelectorAll(".genarateRating").forEach(function (element) {
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

async function handleGetTourImages(tourId) {
    await getApi(`/api/tour/${tourId}/tour-image`, renderTourImages);

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

/*PAGINATION USER REVIEWS*/
var tourReviews = [];
var currentPage = 1;
var perPage = 1;
var totalPage = 0;
var pertourReviews = [];

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

async function renderUsersReviews(tourReviews) {
    try {
        const boxReview = document.querySelector("#boxReview");
        if (boxReview) {
            var htmls = '';
            for (const tourReview of tourReviews) {
                htmls += `<hr />
                        <div class="review_strip_single">
                        <img
                            src="/assets/user/images/avatar3.jpg"
                            alt="Image"
                            class="rounded-circle"
                        />
                        <small> - ${tourReview.editDate ? "Chỉnh sửa - " + tourReview.editDate : tourReview.dateOfPosting} -</small>
                        <h4>${(await getApi(`/api/users/${tourReview.id}`, "NotCallBack")).name}</h4>
                        <p>
                            ${tourReview.content}
                        </p>
                        <div class="rating userRating">
                            ${renderUserRating(parseInt(tourReview.vote))}
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

function handleUserReviewsPagesNumber() {
    pertourReviews = tourReviews.slice(
        (currentPage - 1) * perPage, (currentPage - 1) * perPage + perPage
    );

    renderUsersReviews(pertourReviews);
}

async function handleRenderUserReviews(tourId) {
    try {
        tourReviews = await getApi(`/api/tours/${tourId}/tour-review`, "NotCallBack");
        tourReviews.reverse();

        pertourReviews = tourReviews.slice(
            (currentPage - 1) * perPage, (currentPage - 1) * perPage + perPage
        );

        // render page number
        totalPage = tourReviews.length / perPage;

        if (totalPage > 1) {
            const pagination = document.querySelector("ul.pagination");
            if (pagination) {
                pagination.innerHTML += `
                <li class="page-item page-item-previous" style="pointer-events: none;">
                    <span class="page-link" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                        <span class="sr-only">Previous</span>
                    </span>
                </li>
                `;
                for (var i = 1; i <= totalPage; i++) {
                    pagination.innerHTML += `
                    <li class="page-item page-item-number page-item-number-${i} ${i === 1 ? "active" : ''}" data-number="${i}"><span class="page-link">${i}</span></li>
                    `;
                }
                pagination.innerHTML += `
                <li class="page-item page-item-next">
                    <span class="page-link" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                        <span class="sr-only">Next</span>
                    </span>
                </li>
                `;
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
                    handleUserReviewsPagesNumber();
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
                handleUserReviewsPagesNumber(parseInt(currentPage));
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
                handleUserReviewsPagesNumber(parseInt(currentPage));
            });
        }
        renderUsersReviews(pertourReviews);
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}
/*END PAGINATION USER REVIEWS*/

// function statusButton(quantity) {
//     if (quantity == 1)
//         return 1;
//     else if (quantity >)
// }

// function handleBookingQuantity(priceForAdult, priceForChildren) {
//     try {
//         const adult = document.querySelector("#adults");
//         const adultInc = document.querySelector("#adults-numbers-row .inc");
//         const adultDec = document.querySelector("#adults-numbers-row .dec");
//         const children = document.querySelector("#children");
//         const childrenInc = document.querySelector("#children-numbers-row .inc");
//         const childrenDec = document.querySelector("#children-numbers-row .dec");

//         if (adult && adultInc && adultDec && children && childrenInc && childrenDec) {
//             let adultQuantity = adult.value;
//             let childrenQuantity = children.value;

//             adultQuantity == 1

//             adultInc.addEventListener()
//         } else {
//             throw new Error(`>>> Element with selector '#adults-numbers-row .inc or #adults-numbers-row .dec or #children-numbers-row .inc or #children-numbers-row .dec or "#adults or #children' not found in the DOM`);
//         }

//     } catch (error) {
//         console.log(">>> Error: " + error.message);
//     }
// }

/*BOOKING*/
let deparuteDayIdForUpdate;

function updateBookingQuantity() {
    setInterval(function () {
        console.log("Đã qua 1 giây với deparuteDayId : " + deparuteDayIdForUpdate);
    }, 1000);
}

async function bookingOverLay(tourId) {
    try {
        var departureDays = await getApi(`/api/tour/${tourId}/departure-day`, "NotCallBack");
        var validDepartureDays = departureDays.filter(departureDay => departureDay.status && compareDate(departureDay.departureDay) && departureDay.quantity > 0);

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
            selectedValueInputs.forEach(function (option) {
                option.addEventListener('click', function () {
                    console.log('Đã chọn giá trị mới:', option.querySelector('.dd-option-value').value);
                    deparuteDayIdForUpdate = parseInt(document.querySelector(".dd-option-selected .dd-option-value").value);
                });
            });
        } else {
            console.log("Not found in DOM");
        }
    } catch (error) {
        console.log(">>> Error when getting url parameter: " + error.message);
    }
}

async function handleBooking(tourId) {
    if (!(await bookingOverLay(tourId))) {
        await getDropList(`/api/tour/${tourId}/departure-day`, renderDepartureDropList, "#departureDays", "all_tours.png");

        // Set departureDayId ban đầu
        deparuteDayIdForUpdate = parseInt(document.querySelector(".dd-option-selected .dd-option-value").value);
        // Chạy cập nhật
        updateBookingQuantity();

        changeDepartureDay();
    } else {
        ddslick("#departureDays");
    }
}
/*END BOOKING*/

async function getParamId() {
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

import { getDropList, renderToursRating, getApi, renderDepartureDropList, compareDate, ddslick } from './global_function.js';

async function start() {
    try {
        const tour = await getApi(`/api/tours/${await getParamId()}`, "NotCallBack");
        handleBooking(tour.id);
        renderTourHeader(tour);
        handleGetTourImages(tour.id);
        renderInfo(tour.description, ".description-content");
        renderInfo(tour.schedule, ".schedule-content");
        renderInfo(tour.service, ".service-content");
        handleRenderUserReviews(tour.id);

    } catch (error) {
        console.error(">>> Error:", error.message);
    }
}

document.addEventListener("DOMContentLoaded", function () {
    start();
})