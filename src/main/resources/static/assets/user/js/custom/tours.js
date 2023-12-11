/*TOURS AND PAGINATION*/
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

let tours = [];
let currentPage = 1;
let perPage = 2;
let totalPage = 0;
let originTours = [];
let sortByPriceDec = [];
let sortByPriceInc = [];
let sortByRatingDec = [];
let sortByRatingInc = [];

async function renderTours(perTours, fatherBlock) {
  try {
    let htmls = '';
    for (const perTour of perTours) {
      if (perTour.status) {
        htmls +=
          `<div class="strip_all_tour_list wow fadeIn" data-wow-delay="0.1s">
          <div class="row">
              <div class="col-lg-4 col-md-4">
              ${perTour.isHot ? `<div class="ribbon_3 popular"><span>Popular</span></div>` : ''}
              <div class="img_list">
                  <a href="/tours/${perTour.id}"
                  ><img src="${perTour.thumbnail}" alt="${perTour.name}" />
                  <div class="short_info"><i class="icon_set_1_icon-4"></i></div>
                  </a>
              </div>
              </div>
              <div class="col-lg-6 col-md-6">
              <div class="tour_list_desc">
                  <div class="tourRating">
                    ${await renderToursRating(perTour.id)}
                  </div>
                  <h3><strong>${perTour.name}</strong></h3>
                  <p>${perTour.description}</p>
              </div>
              </div>
              <div class="col-lg-2 col-md-2">
              <div class="price_list">
                  <div>
                  ${perTour.priceForAdult}.0<span
                      class="normal_price_list"
                  ></span
                  <p>
                      <a href="/tours/${perTour.id}" class="btn_1">Chi tiết</a>
                  </p>
                  </div>
              </div>
              </div>
            </div>
        </div>
        <!--End strip -->
        `;
      }
    }
    fatherBlock.innerHTML = htmls;
  } catch (error) {
    console.log(">>> Error: " + error.message);
  }
}

function handleToursPagesNumber(fatherBlock) {
  renderTours(tours.slice(
    (currentPage - 1) * perPage, (currentPage - 1) * perPage + perPage
  ), fatherBlock);
}

async function handleRenderTours(data, fatherBlock) {
  try {
    originTours = tours = data;
    // render page number
    totalPage = Math.ceil(tours.length / perPage);

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
          handleToursPagesNumber(fatherBlock);
        })
      });

      pageItemPrevious.addEventListener("click", function () {
        pageItemsNumber.forEach(function (item) {
          item.classList.remove("active");
        });
        pageItemNext.removeAttribute("style");
        if (--currentPage === 1) {
          this.setAttribute("style", "pointer-events: none;");
        }
        document.querySelector(`.page-item-number-${currentPage}`).classList.add("active");
        handleToursPagesNumber(fatherBlock);
      });

      pageItemNext.addEventListener("click", function () {
        pageItemsNumber.forEach(function (item) {
          item.classList.remove("active");
        });
        pageItemPrevious.removeAttribute("style");
        if (++currentPage === totalPage) {
          this.setAttribute("style", "pointer-events: none;");
        }
        document.querySelector(`.page-item-number-${currentPage}`).classList.add("active");
        handleToursPagesNumber(fatherBlock);
      });
    }
    renderTours(tours.slice(
      (currentPage - 1) * perPage, (currentPage - 1) * perPage + perPage
    ), fatherBlock);
    sortByPrice();
    sortByReviewScore();
  } catch (error) {
    console.log(">>> Error: " + error.message);
  }
}
/*END TOURS AND PAGINATION*/

/*SORT TOURS*/
function funcSortByPriceDec(fatherBlock) {
  if (sortByPriceDec.length === 0) {
    sortByPriceDec = tours.slice();
    tours = sortByPriceDec.sort(function (a, b) {
      return b.priceForAdult - a.priceForAdult;
    });
  } else {
    tours = sortByPriceDec;
  }
  renderTours(sortByPriceDec.slice(
    (currentPage - 1) * perPage, (currentPage - 1) * perPage + perPage
  ), fatherBlock);
}

function funcSortByPriceInc(fatherBlock) {
  if (sortByPriceInc.length === 0) {
    sortByPriceInc = tours.slice();
    tours = sortByPriceInc.sort(function (a, b) {
      return a.priceForAdult - b.priceForAdult;
    });
  } else {
    tours = sortByPriceInc;
  }
  renderTours(sortByPriceInc.slice(
    (currentPage - 1) * perPage, (currentPage - 1) * perPage + perPage
  ), fatherBlock);
}

function sortByPrice() {
  try {
    const sortBlock = document.getElementById("sort_price");

    if (sortBlock) {
      sortBlock.addEventListener("change", function () {
        const sortBlockClose = document.getElementById("sort_rating");
        if (sortBlockClose)
          sortBlockClose.value = '';
        else
          throw new Error(">>> Element with id '#sort_rating' not found in the DOM");

        const fatherBlock = document.querySelector("#tours_list");
        if (fatherBlock) {
          if (sortBlock.value === 'decrease') {
            funcSortByPriceDec(fatherBlock);
          } else if (sortBlock.value === 'ascending') {
            funcSortByPriceInc(fatherBlock);
          } else {
            tours = originTours.slice();
            renderTours(tours.slice(
              (currentPage - 1) * perPage, (currentPage - 1) * perPage + perPage
            ), fatherBlock);
          }
        } else {
          throw new Error(">>> Element with id '#tours_list' not found in the DOM");
        }
      });
    } else {
      throw new Error(">>> Element with id '#sort_price' not found in the DOM");
    }
  } catch (error) {
    console.log(">>> Error: " + error.message);
  }
}

async function getReviewScore(tourId) {
  try {
    let tourReviews = await getApi(`/api/tour/${tourId}/tour-reviews`, "NotCallBack");
    if (tourReviews.length === 0)
      return 0;
    let totalRating = 0;
    let totalQuantity = 0;
    for (let tourReview of tourReviews) {
      totalRating += tourReview.vote;
      totalQuantity++;
    }
    return Math.floor(totalRating / totalQuantity);
  } catch (error) {
    console.log(">>> Error: " + error.message);
    throw error; // Re-throw the error to be caught later
  }
}

async function funcSortByRatingDec(fatherBlock) {
  if (sortByRatingDec.length === 0) {
    sortByRatingDec = tours.slice();
    tours = sortByRatingDec = (await Promise.all(
      sortByRatingDec.map(async (tour) => {
        return {
          tour,
          reviewScore: await getReviewScore(tour.id),
        };
      })
    )).sort((a, b) => b.reviewScore - a.reviewScore).map(item => item.tour);
    tours = sortByRatingDec;
  } else {
    tours = sortByRatingDec;
  }
  renderTours(sortByRatingDec.slice(
    (currentPage - 1) * perPage, (currentPage - 1) * perPage + perPage
  ), fatherBlock);
}

async function funcSortByRatingInc(fatherBlock) {
  if (sortByRatingInc.length === 0) {
    sortByRatingInc = tours.slice();
    tours = sortByRatingInc = (await Promise.all(
      sortByRatingInc.map(async (tour) => {
        return {
          tour,
          reviewScore: await getReviewScore(tour.id),
        };
      })
    )).sort((a, b) => a.reviewScore - b.reviewScore).map(item => item.tour);
  } else {
    tours = sortByRatingInc;
  }
  renderTours(sortByRatingInc.slice(
    (currentPage - 1) * perPage, (currentPage - 1) * perPage + perPage
  ), fatherBlock);
}

function sortByReviewScore() {
  try {
    const sortBlock = document.getElementById("sort_rating");

    if (sortBlock) {
      sortBlock.addEventListener("change", async function () {
        const sortBlockClose = document.getElementById("sort_price");
        if (sortBlockClose)
          sortBlockClose.value = '';
        else
          throw new Error(">>> Element with id '#sort_price' not found in the DOM");

        const fatherBlock = document.querySelector("#tours_list");
        if (fatherBlock) {
          if (sortBlock.value === 'decrease') {
            funcSortByRatingDec(fatherBlock);
          } else if (sortBlock.value === 'ascending') {
            funcSortByRatingInc(fatherBlock);
          } else {
            tours = originTours.slice();
            renderTours(tours.slice(
              (currentPage - 1) * perPage, (currentPage - 1) * perPage + perPage
            ), fatherBlock);
          }
        } else {
          throw new Error(">>> Element with id '#tours_list' not found in the DOM");
        }
      });
    } else {
      throw new Error(">>> Element with id '#sort_price' not found in the DOM");
    }
  } catch (error) {
    console.log(">>> Error: " + error.message);
  }
}
/*END SORT TOURS*/

import { getDropList, renderSearchDropList, handleGetTours, renderToursRating, getApi } from './global_function.js';

function start() {
  getDropList("/api/destinations", renderSearchDropList, "#destinationsDropList", "walking.png");
  getDropList("/api/types-of-tours", renderSearchDropList, "#typeOfTourDropList", "all_tours.png");
  handleGetTours(handleRenderTours, "#tours_list");
}

document.addEventListener("DOMContentLoaded", function () {
  start();
})
