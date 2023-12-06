/*TOURS HAVE DEPARTURE DAY*/
/*
async function renderToursHaveValidDepartureDays(tours, fatherBlock) {
  tours.reverse();
  for (const tour of tours) {
    if (tour.status) {
      var departureDays = await getApi(`/api/tour/${tour.id}/departure-day`, "NotCallBack");
      var validDepartureDays = departureDays.filter(departureDay => departureDay.status && compareDate(departureDay.departureDay) && departureDay.quantity > 0);

      if (validDepartureDays.length > 0) {
        const html =
          `<div class="strip_all_tour_list wow fadeIn" data-wow-delay="0.1s">
          <div class="row">
              <div class="col-lg-4 col-md-4">
              ${tour.isHot ? `<div class="ribbon_3 popular"><span>Popular</span></div>` : ''}
              <div class="img_list">
                  <a href="/tours/${tour.id}"
                  ><img src="${tour.thumbnail}" alt="${tour.name}" />
                  <div class="short_info"><i class="icon_set_1_icon-4"></i></div>
                  </a>
              </div>
              </div>
              <div class="col-lg-6 col-md-6">
              <div class="tour_list_desc">
                  <div class="tourRating">
                    ${await renderToursRating(tour.id)}
                  </div>
                  <h3><strong>${tour.name}</strong></h3>
                  <p>${tour.description}</p>
              </div>
              </div>
              <div class="col-lg-2 col-md-2">
              <div class="price_list">
                  <div>
                  ${tour.priceForAdult}.0<span
                      class="normal_price_list"
                  ></span
                  <p>
                      <a href="/tours/${tour.id}" class="btn_1">Chi tiết</a>
                  </p>
                  </div>
              </div>
              </div>
            </div>
        </div>
        <!--End strip -->
        `;
        fatherBlock.insertAdjacentHTML("beforeend", html);
      }
    }
  }
}*/
async function renderTours(tours, fatherBlock) {
  tours.reverse();
  for (const tour of tours) {
    if (tour.status) {
      const html =
        `<div class="strip_all_tour_list wow fadeIn" data-wow-delay="0.1s">
          <div class="row">
              <div class="col-lg-4 col-md-4">
              ${tour.isHot ? `<div class="ribbon_3 popular"><span>Popular</span></div>` : ''}
              <div class="img_list">
                  <a href="/tours/${tour.id}"
                  ><img src="${tour.thumbnail}" alt="${tour.name}" />
                  <div class="short_info"><i class="icon_set_1_icon-4"></i></div>
                  </a>
              </div>
              </div>
              <div class="col-lg-6 col-md-6">
              <div class="tour_list_desc">
                  <div class="tourRating">
                    ${await renderToursRating(tour.id)}
                  </div>
                  <h3><strong>${tour.name}</strong></h3>
                  <p>${tour.description}</p>
              </div>
              </div>
              <div class="col-lg-2 col-md-2">
              <div class="price_list">
                  <div>
                  ${tour.priceForAdult}.0<span
                      class="normal_price_list"
                  ></span
                  <p>
                      <a href="/tours/${tour.id}" class="btn_1">Chi tiết</a>
                  </p>
                  </div>
              </div>
              </div>
            </div>
        </div>
        <!--End strip -->
        `;
      fatherBlock.insertAdjacentHTML("beforeend", html);
    }
  }
}
/*END TOURS HAVE DEPARTURE DAY*/
import { getDropList, renderSearchDropList, handleGetTours, renderToursRating, getApi, compareDate } from './global_function.js';

function start() {
  getDropList("/api/destinations", renderSearchDropList, "#destinationsDropList", "walking.png");
  getDropList("/api/types-of-tours", renderSearchDropList, "#typeOfTourDropList", "all_tours.png");
  handleGetTours(renderTours, "#tours_list")
}

document.addEventListener("DOMContentLoaded", function () {
  start();
})