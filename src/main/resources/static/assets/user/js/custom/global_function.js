export function compareDate(dateStr) {
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
        return true;
    } else if (inputYear < currentYear) {
        return false;
    } else {
        // Nếu cùng năm, so sánh tháng
        if (inputMonth > currentMonth) {
            return true;
        } else if (inputMonth < currentMonth) {
            return false;
        } else {
            // Nếu cùng tháng, so sánh ngày
            if (inputDay > currentDay) {
                return true;
            } else
                return false;
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

export function renderDepartureDropList(data, fatherBlock, icon) {
    try {
        var htmls = data.map(item =>
            item.status && item.status && compareDate(item.departureDay) && item.quantity > 0 ?
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

export function renderDeparturesDropList(data, fatherBlock, icon) {
    try {
        var htmls = data.map(item =>
            item.status && compareDate(item.departureDay) && item.quantity > 0 ?
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
        const tourReviews = await getApi(`/api/tours/${tourId}/tour-review`, "NotCallBack");

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