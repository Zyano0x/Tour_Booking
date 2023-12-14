export function compareDates(dateString1, dateString2) {
    if (dateString1 < dateString2) {
        return -1;
    } else if (dateString1 > dateString2) {
        return 1;
    } else {
        return 0;
    }
}


export function dateFormatConvert(date) {
    var parts = date.split("-");
    var d = parts[0];
    var m = parts[1];
    var y = parts[2];

    var dateObj = new Date(`${y}-${m}-${d}`);

    return dateObj;
}

export function compareDateNow(dateStr) {
    /*Phải chuyển đổi định dạng dd-MM-yyyy sang "MM/dd/yyyy" hoặc "yyyy-MM-dd" để JavaScript có thể hiểu */
    const dateParts = dateStr.split("-");
    const inputDate = new Date(dateParts[2], dateParts[1] - 1, dateParts[0]);

    // Đối tượng Date hiện tại
    const currentDate = new Date();

    // Lấy thông tin về ngày, tháng, năm từ các đối tượng Date
    const inputDay = inputDate.getDate();
    const inputMonth = inputDate.getMonth();
    const inputYear = inputDate.getFullYear();

    const currentDay = currentDate.getDate();
    const currentMonth = currentDate.getMonth();
    const currentYear = currentDate.getFullYear();

    // So sánh ngày tháng năm
    if (inputYear > currentYear) {
        return 1;
    } else if (inputYear < currentYear) {
        return 0;
    } else {
        // Nếu cùng năm, so sánh tháng
        if (inputMonth > currentMonth) {
            return 0;
        } else if (inputMonth < currentMonth) {
            return 0;
        } else {
            // Nếu cùng tháng, so sánh ngày
            if (inputDay > currentDay) {
                return 1;
            } else if (inputDay < currentDay) {
                return 0;
            }
            else
                return -1;
        }
    }
}

export function ddslick(selector) {
    if (typeof $().ddslick === "function") {
        $(`select${selector}.ddslick`).each(function () {
            // Kiểm tra xem phần tử có tùy chọn không
            $(this).ddslick({
                showSelectedHTML: true,
            });
        });
    } else {
        console.error("ddslick library is not loaded");
    }
}

export function changeQuantity(priceForAdult, priceForChildren) {
    try {
        const totalBlock = document.getElementById("total");

        if (!totalBlock) {
            throw new Error(">>> Element with selector '#total' not found in the DOM");
        }
        totalBlock.innerText = priceForAdult;

        const adult = document.getElementById("adults");
        const adultInc = document.querySelector("#adults-numbers-row .inc");
        const adultDec = document.querySelector("#adults-numbers-row .dec");
        const children = document.getElementById("children");
        const childrenInc = document.querySelector("#children-numbers-row .inc");
        const childrenDec = document.querySelector("#children-numbers-row .dec");

        if (!adult || !adultInc || !adultDec || !children || !childrenInc || !childrenDec) {
            throw new Error(`>>> Element with selector '#adults-numbers-row .inc or #adults-numbers-row .dec or #children-numbers-row .inc or #children-numbers-row .dec or #adults or #children' not found in the DOM`);
        }

        const btnStatus = (element, option) => {
            if (option) {
                adultDec.style.pointerEvents = element.value === '0' ? "none" : "";
            } else {
                childrenDec.style.pointerEvents = element.value === '0' ? "none" : "";
            }
        };

        const updateTotal = (quantity, price) => {
            totalBlock.innerText = parseInt(totalBlock.innerText, 10) + quantity * price;
        };

        btnStatus(children, 0);
        adultInc.addEventListener("click", () => {
            updateTotal(1, priceForAdult)
            btnStatus(adult, 1);
        });
        adultDec.addEventListener("click", () => {
            updateTotal(-1, priceForAdult);
            btnStatus(adult, 1);
        });
        childrenInc.addEventListener("click", () => {
            updateTotal(1, priceForChildren)
            btnStatus(children, 0);
        });
        childrenDec.addEventListener("click", () => {
            updateTotal(-1, priceForChildren);
            btnStatus(children, 0);
        });
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}

export function renderSearchDropList(data, fatherBlock, icon) {
    try {
        var htmls = data.map(item =>
            item.status ?
                `<option
                    value="${item.id}"
                    data-imagesrc="/assets/user/images/icons_search/${icon}">
                    ${item.name}
                </option>`
                : ''
        );
        fatherBlock.insertAdjacentHTML("beforeend", htmls.join(''));
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}

export function renderDepartureDropList(data, fatherBlock, icon, option) {
    try {
        if (option) {
            var htmls = data.map(item =>
                item.status && compareDateNow(item.departureDay) && item.quantity > 0 ?
                    `<option
                    value="${item.id}"
                    data-imagesrc="/assets/user/images/icons_search/${icon}">
                    ${item.departureDay}
                </option>`
                    : ''
            );
        } else {
            var htmls = data.map(item =>
                `<option
                    value="${item.id}"
                    data-imagesrc="/assets/user/images/icons_search/${icon}">
                    ${item.departureDay}
                </option>`
            );
        }
        fatherBlock.insertAdjacentHTML("beforeend", htmls.join(''));
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}

export function renderDeparturesDropList(data, fatherBlock, icon) {
    try {
        var htmls = data.map(item =>
            item.status && compareDateNow(item.departureDay) && item.quantity > 0 ?
                `<option
                    value="${item.id}"
                    data-imagesrc="/assets/user/images/icons_search/${icon}">
                    ${item.departureDay}
                </option>`
                : ''
        );
        fatherBlock.insertAdjacentHTML("beforeend", htmls.join(''));
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}

export async function renderToursRating(tourId) {
    try {
        const tourReviews = await getApi(`/api/tour/${tourId}/tour-reviews`, "NotCallBack");

        let rating = ``;
        if (tourReviews.length === 0) {
            rating = `<i class="icon-smile"></i><i class="icon-smile"></i><i class="icon-smile"></i><i class="icon-smile"></i><i class="icon-smile"></i><small>(0)</small>`;
        } else {
            let score = 0, quantity = 0;
            tourReviews.forEach(tourReview => {
                score += tourReview.vote;
                quantity++;
            });
            score = Math.floor(score / quantity);
            for (let i = 0; i < 5; i++) {
                if (score > 0) {
                    rating += `<i class="icon-smile voted"></i>`;
                    score--;
                } else {
                    rating += `<i class="icon-smile"></i>`;
                }
            }
            rating += `<small>(${quantity})</small>`;
        }
        return rating;
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}

export async function handleGetTours(callBack, fatherBlockSelector) {
    try {
        const fatherBlock = document.querySelector(fatherBlockSelector);
        if (fatherBlock) {
            await getApi("/api/tours", "NotCallBack")
                .then(tours => callBack(tours.reverse(), fatherBlock))
                .catch(error => { throw new Error(error) });
        } else {
            throw new Error("Element with id '#favourite-tours' not found in the DOM");
        }
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}

export async function getApi(apiUrl, callBack) {
    try {
        const response = await fetch(apiUrl);
        if (!response.ok)
            throw new Error(`>>> Response was not ok: ${response.statusText}`);
        const data = await response.json();
        if (typeof callBack === 'function')
            callBack(data);
        else
            return data;
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}

export async function getDropList(apiUrl, callBack, fatherBlockSelector, icon) {
    try {
        const fatherBlock = document.querySelector(fatherBlockSelector);
        if (fatherBlock) {
            const data = await getApi(apiUrl, "NotCallBack");
            callBack(data, fatherBlock, icon);

            ddslick(fatherBlockSelector);
        } else
            throw new Error(`Element with id ${fatherBlockSelector} not found in the DOM`);
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}
