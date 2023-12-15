/*TOURS AND PAGINATION*/
/*
async function renderToursHaveValidDepartureDays(tours, fatherBlock) {
  tours.reverse();
  for (const tour of tours) {
    if (tour.status) {
      var departureDays = await getApi(`/api/tour/${tour.id}/departure-day`, "NotCallBack");
      var validDepartureDays = departureDays.filter(departureDay => departureDay.status && compareDateNow(departureDay.departureDay) && departureDay.quantity > 0);

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
// let sortByPriceDec = [];
// let sortByPriceInc = [];
// let sortByRatingDec = [];
// let sortByRatingInc = [];
let toursFilter = [];
const sortPriceBlock = document.getElementById("sort_price");
const sortRatingBlock = document.getElementById("sort_rating");

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

function renderToursPagesNumber(fatherBlock) {
  try {
    const pagination = document.querySelector("ul.pagination");
    if (!pagination)
      throw new Error(`>>> Element with selector 'ul.pagination' not found in the DOM`);

    totalPage = Math.ceil(tours.length / perPage);
    if (totalPage > 1) {
      let htmls = '';
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
    } else {
      pagination.innerHTML = '';
    }
  } catch (error) {
    console.log(">>> Error: " + error.message);
  }
}

async function handleRenderTours(data, fatherBlock) {
  try {
    originTours = tours = data;
    // render page number

    // render pages number
    renderToursPagesNumber(fatherBlock);

    renderTours(tours.slice(
      (currentPage - 1) * perPage, (currentPage - 1) * perPage + perPage
    ), fatherBlock);
    handleSortByPrice();
    handleSortByReviewScore();
  } catch (error) {
    console.log(">>> Error: " + error.message);
  }
}
/*END TOURS AND PAGINATION*/

/*SORT TOURS*/
function funcSortByPriceDec(fatherBlock) {
  tours.sort(function (a, b) {
    return b.priceForAdult - a.priceForAdult;
  });
  renderTours(tours.slice(
    (currentPage - 1) * perPage, (currentPage - 1) * perPage + perPage
  ), fatherBlock);
}

function funcSortByPriceInc(fatherBlock) {
  tours.sort(function (a, b) {
    return a.priceForAdult - b.priceForAdult;
  });
  renderTours(tours.slice(
    (currentPage - 1) * perPage, (currentPage - 1) * perPage + perPage
  ), fatherBlock);
}

function SortByPrice() {
  try {
    if (sortRatingBlock)
      sortRatingBlock.value = '';
    else
      throw new Error(">>> Element with id '#sort_rating' not found in the DOM");

    const fatherBlock = document.querySelector("#tours_list");
    if (fatherBlock) {
      if (sortPriceBlock.value === 'decrease') {
        funcSortByPriceDec(fatherBlock);
      } else if (sortPriceBlock.value === 'ascending') {
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
  } catch (error) {
    console.log(">>> Error: " + error.message);
  }
}

function handleSortByPrice() {
  try {
    if (sortPriceBlock) {
      sortPriceBlock.addEventListener("change", function () {
        SortByPrice();
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
  tours = (await Promise.all(
    tours.map(async (tour) => {
      return {
        tour,
        reviewScore: await getReviewScore(tour.id),
      };
    })
  )).sort((a, b) => b.reviewScore - a.reviewScore).map(item => item.tour);

  renderTours(tours.slice(
    (currentPage - 1) * perPage, (currentPage - 1) * perPage + perPage
  ), fatherBlock);
}

async function funcSortByRatingInc(fatherBlock) {
  tours = (await Promise.all(
    tours.map(async (tour) => {
      return {
        tour,
        reviewScore: await getReviewScore(tour.id),
      };
    })
  )).sort((a, b) => a.reviewScore - b.reviewScore).map(item => item.tour);

  renderTours(tours.slice(
    (currentPage - 1) * perPage, (currentPage - 1) * perPage + perPage
  ), fatherBlock);
}

function SortByReviewScore() {
  try {
    if (sortPriceBlock)
      sortPriceBlock.value = '';
    else
      throw new Error(">>> Element with id '#sort_price' not found in the DOM");

    const fatherBlock = document.querySelector("#tours_list");
    if (fatherBlock) {
      if (sortRatingBlock.value === 'decrease') {
        funcSortByRatingDec(fatherBlock);
      } else if (sortRatingBlock.value === 'ascending') {
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
  } catch (error) {
    console.log(">>> Error: " + error.message);
  }
}

function handleSortByReviewScore() {
  try {
    if (sortRatingBlock) {
      sortRatingBlock.addEventListener("change", async function () {
        SortByReviewScore();
      });
    } else {
      throw new Error(">>> Element with id '#sort_price' not found in the DOM");
    }
  } catch (error) {
    console.log(">>> Error: " + error.message);
  }
}
/*END SORT TOURS*/

/*TOURS FILTER*/
async function funcToursFilter(fatherBlock, filterDatePick) {
  try {
    tours = originTours.slice();
    const filterDestination = document.querySelector("#destinationsDropList .dd-option-selected .dd-option-value");
    const filterTOT = document.querySelector("#typeOfTourDropList .dd-option-selected .dd-option-value");

    if (filterDestination && filterDestination.value != '0') {
      tours = tours.filter(tour => {
        return tour.destination.id == filterDestination.value;
      });
    }
    if (filterTOT && filterTOT.value != '0') {
      tours = tours.filter(tour => {
        return tour.typeOfTour.id == filterTOT.value;
      });
    }
    if (filterDatePick && filterDatePick.value != '' && compareDateNow(filterDatePick.value)) {
      let temp = [];

      for (const tour of tours) {
        let departureDays = await getApi(`/api/tour/${tour.id}/departure-days`);
        departureDays = departureDays.filter(departureDay => departureDay.status && compareDates(departureDay.departureDay, filterDatePick.value) === 0);
        if (departureDays.length > 0) {
          temp.push(tour);
        }
      }
      tours = temp;
    }

    if (!(JSON.stringify(tours) === JSON.stringify(toursFilter))) {
      currentPage = 1;
      if (sortPriceBlock.value != '') {
        SortByPrice(sortPriceBlock);
      } else if (sortRatingBlock.value != '') {
        SortByReviewScore(sortRatingBlock);
      } else {
        renderTours(tours.slice(0, perPage), fatherBlock);
      }
      renderToursPagesNumber(fatherBlock);
      toursFilter = tours.slice();
    }
  } catch (error) {
    console.log(">>> Error: " + error.message);
  }
}

function handleToursFilter(selector) {
  try {
    const fatherBlock = document.querySelector(selector);
    const filterDatePick = document.querySelector(".date-pick");
    if (filterDatePick)
      filterDatePick.value = '';

    if (fatherBlock) {
      const filterBtn = document.querySelector(".filter-btn");
      if (filterBtn) {
        toursFilter = originTours.slice();
        filterBtn.addEventListener("click", async function () {
          funcToursFilter(fatherBlock, filterDatePick);
        });
      }
    }
  } catch (error) {
    console.log(">>> Error: " + error.message);
  }
}
/*END TOURS FILTER*/


import { getDropList, renderSearchDropList, handleGetTours, renderToursRating, getApi, compareDates, compareDateNow } from './global_function.js';

function start() {
  handleGetTours(handleRenderTours, "#tours_list");
  Promise.all([getDropList("/api/destinations", renderSearchDropList, "#destinationsDropList", "walking.png"), getDropList("/api/types-of-tours", renderSearchDropList, "#typeOfTourDropList", "all_tours.png")]).then(data => handleToursFilter("#tours_list")).catch(error => console.log(">>> Error: " + error.message));
}

document.addEventListener("DOMContentLoaded", function () {
  start();
})
