let bookings = [];

/*GET BOOKINGS*/
// Booking còn hạn
/*
async function handleRenderValidBookings(fatherBlock) {
    try {
        for (let i = bookings.length - 1; i >= 0; i--) {
            let htmls = ``;
            let departureDayCurrent = bookings[i].departureDay;

            if (bookings[i].status && compareDateNow(departureDayCurrent.departureDay)) {
                let tour = departureDayCurrent.tour;
                let departureDays = await getApi(`/api/tour/${tour.id}/departure-days`);

                // Lọc departureDays dựa trên các điều kiện
                departureDays = departureDays.filter(departureDay => departureDay.status &&
                    compareDateNow(departureDay.departureDay) &&
                    departureDay.quantity > 0 && departureDay.id != departureDayCurrent.id);

                // Kiểm tra departureDayCurrent.quantity và thêm nó vào mảng nếu cần
                departureDays.push(departureDayCurrent);

                htmls += `
                <tr id="booking${bookings[i].id}">
                    <td class="cart-td-tour-info">
                        <div class="thumb_cart">
                            <img src="${tour.thumbnail}" alt="Image" />
                        </div>
                        <span class="item_cart">${tour.name}</span>
                    </td>
                    <td>
                        <span class="adults-quantity text-align-center">${bookings[i].quantityOfAdult}</span>
                    </td>
                    <td>
                        <span class="children-quantity text-align-center">${bookings[i].quantityOfChild}</span>
                    </td>
                    <td>
                        <div class="form-group none-margin-bottom" data-presentDepartureId="${departureDayCurrent.id}">
                            <select 
                            id="departureDays${bookings[i].id}"
                            class="ddslick"
                            name="departureDays"
                            ></select>
                        </div>
                    </td>
                    <td>
                        <div class="departureTime text-align-center"></div>
                    </td>
                    <td class="cart-td-departure-point">
                        <span class="departurePoint">${tour.departurePoint}</span>
                    </td>
                    <td>
                        <div class="text-align-center"><strong>${moneyFormat(bookings[i].totalPrice, true)}</strong></div>
                    </td>
                    <td class="options" data-booking="${bookings[i].id}" >
                        <a class="cancel" href="javascript:void(0);"><i class="icon-trash"></i></a>
                        <a class="update${bookings[i].id}" href="javascript:void(0);"><i class="icon-ccw-2"></i></a>
                    </td>
                </tr>`;

                fatherBlock.innerHTML += htmls;

                let departureDaysBlock = document.querySelector(`#departureDays${bookings[i].id}`);
                let icon = "all_tours.png";

                if (departureDaysBlock) {
                    renderDepartureDropList(departureDays.reverse(), departureDaysBlock, icon, false);
                    // Hiển thị giờ khởi hành ban đầu
                    await getDepartureTime(`.departureTime`, departureDaysBlock.parentNode.dataset.presentdepartureid, `#booking${bookings[i].id}`);
                }

                bookings.splice(i, 1);
            }
        }
        ddslick(`.ddslick`);
        CancelBooking();
        UpdateBooking();
        departureTimeByChangeDepartureDay();
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}
*/

async function handleRenderValidBookings(fatherBlock) {
    try {
        for (let i = bookings.length - 1; i >= 0; i--) {
            let htmls = ``;
            let departureDayCurrent = bookings[i].departureDay;

            if (bookings[i].status && compareDateNow(departureDayCurrent.departureDay) == 1) {
                let tour = departureDayCurrent.tour;
                let departureDays = await getApi(`/api/tour/${tour.id}/departure-days`);

                // Lọc departureDays dựa trên các điều kiện
                departureDays = departureDays.filter(departureDay => departureDay.status &&
                    compareDateNow(departureDay.departureDay) == 1 &&
                    departureDay.quantity > 0 && departureDay.id != departureDayCurrent.id);

                // Kiểm tra departureDayCurrent.quantity và thêm nó vào mảng nếu cần
                departureDays.push(departureDayCurrent);

                htmls += `
                <tr id="booking${bookings[i].id}">
                    <td class="cart-td-tour-info">
                        <div class="thumb_cart">
                            <img src="${tour.thumbnail}" alt="Image" />
                        </div>
                        <span class="item_cart">${tour.name}</span>
                    </td>
                    <td>
                        <span class="cart_time text-align-center">${tour.time}</span>
                    </td>
                    <td>
                        <div class="form-group none-margin-bottom" data-presentDepartureId="${departureDayCurrent.id}">
                            <select 
                            id="departureDays${bookings[i].id}"
                            class="ddslick"
                            name="departureDays"
                            ></select>
                        </div>
                    </td>
                    <td>
                        <div class="departureTime text-align-center"></div>
                    </td>
                    <td class="cart-td-departure-point">
                        <span class="departurePoint">${tour.departurePoint}</span>
                    </td>
                    <td>
                        <span class="adults-quantity text-align-center">${bookings[i].quantityOfAdult}</span>
                    </td>
                    <td>
                        <span class="children-quantity text-align-center">${bookings[i].quantityOfChild}</span>
                    </td>
                    <td>
                        <div class="text-align-center"><strong>${moneyFormat(bookings[i].totalPrice, true)}</strong></div>
                    </td>
                    <td class="options text-align-center" data-booking="${bookings[i].id}" >
                        <a class="cancel" href="javascript:void(0);"><i class="fa-solid fa-trash-can"></i></a>
                        <a class="update${bookings[i].id}" href="javascript:void(0);"><i class="icon-ccw-2"></i></a>
                        <a href="/tours/${tour.id}"><i class="fa-solid fa-eye"></i></a>
                    </td>
                </tr>`;

                fatherBlock.innerHTML += htmls;

                let departureDaysBlock = document.querySelector(`#departureDays${bookings[i].id}`);
                let icon = "all_tours.png";

                if (departureDaysBlock) {
                    renderDepartureDropList(departureDays.reverse(), departureDaysBlock, icon, false);
                    // Hiển thị giờ khởi hành ban đầu
                    await getDepartureTime(`.departureTime`, departureDaysBlock.parentNode.dataset.presentdepartureid, `#booking${bookings[i].id}`);
                }

                bookings.splice(i, 1);
            }
        }
        ddslick(`.ddslick`);
        CancelBooking();
        UpdateBooking();
        departureTimeByChangeDepartureDay();
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}

/*
// Booking hết hạn
async function renderExpiredBookings(fatherBlock) {
    try {
        for (let i = bookings.length - 1; i >= 0; i--) {
            let htmls = ``;
            let departureDayCurrent = bookings[i].departureDay;

            if (bookings[i].status && (!compareDateNow(departureDayCurrent.departureDay))) {
                let tour = departureDayCurrent.tour;

                htmls += `
                <tr>
                    <td class="cart-td-tour-info">
                        <div class="thumb_cart">
                            <img src="${tour.thumbnail}" alt="Image" />
                        </div>
                        <span class="item_cart">${tour.name}</span>
                    </td>
                    <td>
                        <span class="adults-quantity text-align-center">${bookings[i].quantityOfAdult}</span>
                    </td>
                    <td>
                        <span class="children-quantity text-align-center">${bookings[i].quantityOfChild}</span>
                    </td>
                    <td>
                       <div class="text-align-center">${departureDayCurrent.departureDay}</div>
                    </td>
                    <td>
                        <div class="departureTime text-align-center">${bookings[i].departureDay.departureTime}</div>
                    </td>
                    <td class="cart-td-departure-point">
                        <span class="departurePoint">${tour.departurePoint}</span>
                    </td>
                    <td>
                        <div class="text-align-center">
                            <strong>${moneyFormat(bookings[i].totalPrice, true)}</strong>
                        </div>
                    </td>
                    <td class="text-align-center">
                        <a href="/tours/${tour.id}">Xem tour</a>
                    </td>
                </tr>`;

                fatherBlock.innerHTML += htmls;

                bookings.splice(i, 1);
            }
        }
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}
*/

// Booking đã diễn ra
async function renderExpiredBookings(fatherBlock) {
    try {
        for (let i = bookings.length - 1; i >= 0; i--) {
            let htmls = ``;
            let departureDayCurrent = bookings[i].departureDay;

            if (bookings[i].status && compareDateNow(departureDayCurrent.departureDay) != 1) {
                let tour = departureDayCurrent.tour;

                htmls += `
                <tr>
                    <td class="cart-td-tour-info">
                        <div class="thumb_cart">
                            <img src="${tour.thumbnail}" alt="Image" />
                        </div>
                        <span class="item_cart">${tour.name}</span>
                    </td>
                    <td>
                        <span class="cart_time text-align-center">${tour.time}</span>
                    </td>
                    <td>
                       <div class="text-align-center">${departureDayCurrent.departureDay}</div>
                    </td>
                    <td>
                        <div class="departureTime text-align-center">${bookings[i].departureDay.departureTime}</div>
                    </td>
                    <td class="cart-td-departure-point">
                        <span class="departurePoint">${tour.departurePoint}</span>
                    </td>
                    <td>
                        <span class="adults-quantity text-align-center">${bookings[i].quantityOfAdult}</span>
                    </td>
                    <td>
                        <span class="children-quantity text-align-center">${bookings[i].quantityOfChild}</span>
                    </td>
                    <td>
                        <div class="text-align-center">
                            <strong>${moneyFormat(bookings[i].totalPrice, true)}</strong>
                        </div>
                    </td>
                    <td class="text-align-center">
                        <a href="/tours/${tour.id}"><i class="fa-solid fa-eye" style="
                        font-size: 19px;
                        "></i></a>
                    </td>
                </tr>`;

                fatherBlock.innerHTML += htmls;

                bookings.splice(i, 1);
            }
        }
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}

// Booking đã hủy
async function renderCancelledBookings(fatherBlock) {
    try {
        for (const booking of bookings) {
            let htmls = ``;
            let departureDayCurrent = booking.departureDay;

            if (!booking.status) {
                let tour = departureDayCurrent.tour;

                htmls += `
                <tr>
                    <td class="cart-td-tour-info">
                        <div class="thumb_cart">
                            <img src="${tour.thumbnail}" alt="Image" />
                        </div>
                        <span class="item_cart">${tour.name}</span>
                    </td>
                    <td>
                        <span class="cart_time text-align-center">${tour.time}</span>
                    </td>
                    <td>
                       <div class="text-align-center">${departureDayCurrent.departureDay}</div>
                    </td>
                    <td>
                        <div class="departureTime text-align-center">${booking.departureDay.departureTime}</div>
                    </td>
                    <td class="cart-td-departure-point">
                        <span class="departurePoint">${tour.departurePoint}</span>
                    </td>
                    <td>
                        <span class="adults-quantity text-align-center">${booking.quantityOfAdult}</span>
                    </td>
                    <td>
                        <span class="children-quantity text-align-center">${booking.quantityOfChild}</span>
                    </td>
                    <td>
                        <div class="text-align-center">
                            <strong>${moneyFormat(booking.totalPrice, true)}</strong>
                        </div>
                    </td>
                    <td class="text-align-center">
                        <a href="/tours/${tour.id}"><i class="fa-solid fa-eye" style="
                        font-size: 19px;
                        "></i></a>
                    </td>
                </tr>`;

                fatherBlock.innerHTML += htmls;
            }
        }
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}

async function handleGetBooking() {
    try {
        await handleRenderValidBookings(document.querySelector(".valid-bookings"));
        if (bookings.length > 0) {
            await renderExpiredBookings(document.querySelector(".expired-bookings"));
            if (bookings.length > 0) {
                await renderCancelledBookings(document.querySelector(".cancelled-bookings"));
            }
        }
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}
/*END GET BOOKINGS*/

////////////////////////////////////////

/*CANCEL BOOKING*/
function CancelBooking() {
    try {
        const cancelBtns = document.querySelectorAll(".options .cancel");

        if (cancelBtns.length > 0) {
            for (const cancelBtn of cancelBtns) {
                cancelBtn.addEventListener("click", async function () {
                    let booking = await getApi(`/api/booking/${cancelBtn.parentNode.dataset.booking}`);
                    let htmls = `
                    <div class="custom-prompt" id="customPrompt">
                        <div class="prompt-content">
                            <h4>- HỦY ĐẶT VÉ -</h4>
                            <div class="tour-cancel-info">
                                <p>Tour: <strong>${booking.departureDay.tour.name}</strong></p>
                                <p>Ngày khởi hành: <strong>${booking.departureDay.departureDay}</strong></p>
                                <p>Tổng tiền: <strong>${booking.totalPrice}.0 vnd</strong></p>
                            </div>
                            <div class="temp">
                                <p>Số tiền hoàn trả: <strong>${compareDateNow(booking.departureDay.departureDay) === -1 ? booking.totalPrice * 0.7 + ".0 vnd (70%)" : Math.ceil(((new Date(dateFormatConvert(booking.departureDay.departureDay))) - (new Date())) / (1000 * 60 * 60 * 24)) >= 7 ? booking.totalPrice * 0.8 + ".0 vnd (80%)" : booking.totalPrice * 0.7 + ".0 vnd (70%)"}</strong ></p >
                                <p>Hỗ trợ: 0961105747</p>
                            </div >
                            <p>Sau khi xác nhận hủy, sẽ có email xác nhận được gửi đến email đăng ký tài khoản. Vui lòng xác nhận để hoàn tất hủy vé!</p>
                            <div class="action">
                                <button class="action-btn cancel-btn">Hủy</button>
                                <button class="action-btn update-btn">Xác nhận</button>
                            </div>
                        </div >
                    </div > `;
                    document.body.insertAdjacentHTML("beforeend", htmls);
                    const dialog = document.querySelector("#customPrompt");
                    document.querySelector(".action-btn.cancel-btn").addEventListener("click", function () {
                        dialog.parentNode.removeChild(dialog);
                    });
                    document.querySelector(".action-btn.update-btn").addEventListener("click", function () {
                        dialog.parentNode.removeChild(dialog);
                        alertFunc("fa-solid fa-circle-check", "#5cc41a", "#dafbb9", 'Email xác nhận đã được gửi!');
                        const url = `/api/update-booking-status?id=${cancelBtn.parentNode.dataset.booking}`;
                        fetch(url, {
                            method: "PUT",
                            headers: {
                                "Content-Type": "application/json",
                            },
                        })
                            .then((response) => response.json())
                            .then((data) => console.log(data))
                            .catch((error) => {
                                console.error("Error creating payment:", error);
                            });
                    });
                });
            }
        } else {
            throw new Error("Element with selector '.options .cancel' not found in the DOM");
        }
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}
/*END CANCEL BOOKING*/

////////////////////////////////////////

/*UPDATE BOOKING*/
function UpdateBooking() {
    try {
        const selectedOptions = document.querySelectorAll(`.dd-selected`);

        if (selectedOptions.length > 0) {
            for (const selectedOption of selectedOptions) {
                let bookingId = selectedOption.parentNode.parentNode.id.slice().substring("departureDays".length);
                const url = `/api/update-booking/${bookingId}`;

                const updateBtn = document.querySelector(`.update${bookingId}`);

                const departureDaysElement = document.querySelector(`#departureDays${bookingId}`);
                if (departureDaysElement) {
                    updateBtn.addEventListener("click", async function () {
                        let presentDepartureId = departureDaysElement.parentNode.dataset.presentdepartureid;
                        let selectDepartureId = departureDaysElement.querySelector('.dd-selected-value').value;
                        const adult = updateBtn.parentNode.parentNode.querySelector(".adults-quantity");
                        const children = updateBtn.parentNode.parentNode.querySelector(".children-quantity");
                        let totalQuantity = parseInt(adult.textContent) + parseInt(children.textContent);

                        if (selectDepartureId != presentDepartureId) {
                            if (parseInt((await getApi(`/api/departure-day?id=${selectDepartureId}`, "NotCallBack")).quantity) < totalQuantity) {
                                alertFunc("fa-solid fa-circle-exclamation", "#faad14", "#fbf1be", "Số lượng vé đã vượt quá số lượng vé còn lại!");
                            } else {
                                let myHeaders = new Headers();
                                myHeaders.append("Content-Type", "application/json");

                                let raw = JSON.stringify({
                                    "userId": document.head.dataset.userid,
                                    "quantityOfAdult": adult.textContent,
                                    "quantityOfChild": children.textContent,
                                    "departureDayId": selectDepartureId,
                                });

                                let requestOptions = {
                                    method: 'PUT',
                                    headers: myHeaders,
                                    body: raw,
                                    redirect: 'follow'
                                };

                                // Make the API request
                                fetch(url, requestOptions)
                                    .then(response => {
                                        return response.text();
                                    })
                                    .then(result => {
                                        alertFunc("fa-solid fa-circle-check", "#5cc41a", "#dafbb9", 'Cập nhật thành công', result);
                                    })
                                    .catch(error => console.log('Error updating booking:', error));
                                departureDaysElement.parentNode.setAttribute('data-presentdepartureid', `${selectDepartureId}`);
                            }
                        }
                    });
                }
            }
        } else {
            throw new Error("Element with selector '.dd-selected-value' not found in the DOM");
        }
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}
/*END UPDATE BOOKING*/

////////////////////////////////////////

/*DEPARTURE TIME*/
function departureTimeByChangeDepartureDay() {
    const optionsDepartureDay = document.querySelectorAll(".dd-option");
    if (optionsDepartureDay.length > 0) {
        for (const optionDepartureDay of optionsDepartureDay) {
            optionDepartureDay.addEventListener("click", async function () {
                await getDepartureTime(`.departureTime`, optionDepartureDay.querySelector(".dd-option-value").value, `#${optionDepartureDay.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.id}`);
            });
        }
    }
}
/*END DEPARTURE TIME*/

////////////////////////////////////////

import { compareDateNow, dateFormatConvert, ddslick, getApi, moneyFormat, renderDepartureDropList, alertFunc, getDepartureTime } from './global_function.js';

async function start() {
    bookings = await getApi(`/api/user/${document.head.dataset.userid}/bookings`);
    if (bookings.length > 0) {
        handleGetBooking();
    }
}

document.addEventListener("DOMContentLoaded", function () {
    start();
})