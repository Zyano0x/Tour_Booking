let bookings = [];
let bookingOrigin = [];

/*GET BOOKINGS*/
// Booking còn hạn
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
                <tr>
                    <td>
                        <div class="thumb_cart">
                            <img src="${tour.thumbnail}" alt="Image" />
                        </div>
                        <span class="item_cart">${tour.name}</span>
                    </td>
                    <td>
                        <div class="form-group none-margin-bottom">
                            <div id="adults-numbers-row" class="numbers-row margin-auto">
                                <input
                                    type="text"
                                    value="${bookings[i].quantityOfAdult}"
                                    id="adults"
                                    class="qty2 form-control"
                                    name="quantity"
                                />
                                <div class="inc button_inc">+</div>
                                <div class="dec button_inc">-</div>
                            </div>
                        </div>
                    </td>
                    <td>
                        <div class="form-group none-margin-bottom">
                            <div id="children-numbers-row" class="numbers-row margin-auto"> 
                                <input
                                type="text"
                                value="${bookings[i].quantityOfChild}"
                                id="children"
                                class="qty2 form-control"
                                name="quantity"
                                />
                                <div class="inc button_inc">+</div>
                                <div class="dec button_inc">-</div>
                            </div>
                        </div>
                    </td>
                    <td>
                        <div class="form-group none-margin-bottom">
                            <select
                            id="departureDays${bookings[i].id}"
                            class="ddslick"
                            name="departureDays"
                            ></select>
                        </div>
                    </td>
                    <td>
                        <span class="departurePoint text-align-center">${tour.departurePoint}</span>
                    </td>
                    <td>
                        <div class="text-align-center"><strong>${bookings[i].totalPrice}.0</strong></div>
                    </td>
                    <td class="options" data-booking="${bookings[i].id}" >
                        <a class="cancel" href="javascript:void(0);"><i class="icon-trash"></i></a>
                        <a href="#"><i class="icon-ccw-2"></i></a>
                    </td>
                </tr>`;

                fatherBlock.innerHTML += htmls;

                let departureDaysBlock = document.querySelector(`#departureDays${bookings[i].id}`);
                let icon = "all_tours.png";

                if (departureDaysBlock)
                    renderDepartureDropList(departureDays.reverse(), departureDaysBlock, icon, false);

                bookings.splice(i, 1);
            }
        }
        ddslick(`.ddslick`);
        CancelBooking();
        UpdateBooking();
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}

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
                    <td>
                        <div class="thumb_cart">
                            <img src="${tour.thumbnail}" alt="Image" />
                        </div>
                        <span class="item_cart">${tour.name}</span>
                    </td>
                    <td>
                        <div class="form-group none-margin-bottom text-align-center">
                            ${bookings[i].quantityOfAdult}
                        </div>
                    </td>
                    <td>
                        <div class="form-group none-margin-bottom text-align-center">
                            ${bookings[i].quantityOfChild}
                        </div>
                    </td>
                    <td>
                       <div class="text-align-center">${departureDayCurrent.departureDay}</div>
                    </td>
                    <td>
                        <span class="departurePoint text-align-center">${tour.departurePoint}</span>
                    </td>
                    <td>
                        <div class="text-align-center">
                            <strong>${bookings[i].totalPrice}.0</strong>
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
                    <td>
                        <div class="thumb_cart">
                            <img src="${tour.thumbnail}" alt="Image" />
                        </div>
                        <span class="item_cart">${tour.name}</span>
                    </td>
                    <td>
                        <div class="form-group none-margin-bottom text-align-center">
                            ${booking.quantityOfAdult}
                        </div>
                    </td>
                    <td>
                        <div class="form-group none-margin-bottom text-align-center">
                            ${booking.quantityOfChild}
                        </div>
                    </td>
                    <td>
                       <div class="text-align-center">${departureDayCurrent.departureDay}</div>
                    </td>
                    <td>
                        <span class="departurePoint text-align-center">${tour.departurePoint}</span>
                    </td>
                    <td>
                        <div class="text-align-center">
                            <strong>${booking.totalPrice}.0</strong>
                        </div>
                    </td>
                    <td class="text-align-center">
                        <a href="/tours/${tour.id}">Xem tour</a>
                    </td>
                </tr>`;

                fatherBlock.innerHTML += htmls;
            }
        }
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}

function handleGetBooking() {
    try {
        handleRenderValidBookings(document.querySelector(".valid-bookings"));
        if (bookings.length > 0) {
            renderExpiredBookings(document.querySelector(".expired-bookings"));
            if (bookings.length > 0) {
                renderCancelledBookings(document.querySelector(".cancelled-bookings"));
            }
        }
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}
/*END GET BOOKINGS*/

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
                                <p>Số tiền hoàn trả: <strong>${compareDateNow(booking.departureDay.departureDay) === -1 ? booking.totalPrice * 0.7 + ".0 vnd (70%)" : Math.floor(((new Date(dateFormatConvert(booking.departureDay.departureDay))) - (new Date())) / (1000 * 60 * 60 * 24)) > 7 ? booking.totalPrice + ".0 vnd (100%)" : booking.totalPrice * 0.7 + ".0 vnd (70%)"}</strong ></p >
                                <p>Hỗ trợ: 0961105747</p>
                            </div >
                            <p>Sau khi xác nhận hủy, sẽ có email xác nhận được gửi đến email đăng ký tài khoản. Vui lòng xác nhận để hoàn tất hủy vé!</p>
                            <div class="action">
                                <button class="action-btn cancel-btn">Hủy</button>
                                <button class="action-btn update-btn-">Xác nhận</button>
                            </div>
                        </div >
                    </div > `;
                    document.body.insertAdjacentHTML("beforeend", htmls);
                    document.querySelector(".action-btn.cancel-btn").addEventListener("click", function () {
                        const dialog = document.querySelector("#customPrompt");
                        dialog.parentNode.removeChild(dialog);
                    });
                });
            }
        } else {
            throw new Error(">>> Element with id '.options .cancel' not found in the DOM");
        }
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}
/*END CANCEL BOOKING*/

/*END UPDATE BOOKING*/
function UpdateBooking() {
    try {
        const updateBtns = document.querySelectorAll(".options .udpate");

        if (updateBtns.length > 0) {
            for (const updateBtn of updateBtns) {
                updateBtn.addEventListener("click", async function () {

                });
            }
        } else {
            throw new Error(">>> Element with id '.options .udpate' not found in the DOM");
        }
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}
/*END CANCEL BOOKING*/

import { getApi, compareDateNow, renderDepartureDropList, ddslick, dateFormatConvert } from './global_function.js';

async function start() {
    bookingOrigin = bookings = await getApi(`/api/user/${document.head.dataset.userid}/bookings`);
    if (bookings.length > 0) {
        handleGetBooking();
    }
}

document.addEventListener("DOMContentLoaded", function () {
    start();
})
