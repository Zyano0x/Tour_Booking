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
                    ${await renderRating(tour.id)}
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
let perPage = 5;
let originTours = [];
let presentOriginTours = [];
const sortPriceBlock = document.getElementById("sort_price");
const sortRatingBlock = document.getElementById("sort_rating");
const filterBtn = document.querySelector(".filter-btn");
const filterDatePick = document.querySelector(".date-pick");
let fatherBlock;
const destinationFilter = localStorage.getItem("destinationFilter");

async function renderTours(perTours, fatherBlock) {
  try {
    let htmls = '';
    for (const perTour of perTours) {
      if (perTour.status) {
        htmls +=
          `<div class="strip_all_tour_list wow fadeIn" data-wow-delay="0.1s">
          <div class="row">
              <div class="col-lg-4 col-md-4">
              ${perTour.isHot ? `<div class="ribbon_3 popular"><span>Nổi bật</span></div>` : ''}
              <div class="img_list">
                  <a href="/tours/${perTour.id}"
                  ><img src="${perTour.thumbnail}" alt="${perTour.name}" />
                  <div class="short_info"><i class="icon_set_1_icon-4"></i></div>
                  </a>
              </div>
              </div>
              <div class="col-lg-6 col-md-6">
              <div class="list_desc">
                  <div class="rating">
                    ${await renderRating(`/api/v1/tour-reviews/tours/${perTour.id}`)}
                  </div>
                  <h4><strong>${perTour.name}</strong></h4>
                  <p>${perTour.description}</p>
              </div>
              </div>
              <div class="col-lg-2 col-md-2">
              <div class="price_list">
                  <div>
                  ${moneyFormat(perTour.priceForAdult, true)}<span
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

function renderToursPagesNumber(fatherBlock) {
  try {
    const pagination = document.querySelector("ul.pagination");
    if (!pagination)
      throw new Error(`>>> Element with selector 'ul.pagination' not found in the DOM`);

    let totalPage = Math.ceil(tours.length / perPage);
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

      let renderList = () => {
        renderTours(tours.slice(
          (currentPage - 1) * perPage, (currentPage - 1) * perPage + perPage
        ), fatherBlock);
      }

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
          renderList();
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
        renderList();
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
        renderList();
      });
    } else {
      pagination.innerHTML = '';
    }
  } catch (error) {
    console.log(">>> Error: " + error.message);
  }
}

function handleRenderTours(data, fatherBlock) {
  try {
    originTours = data.slice(); // Không thay đổi
    presentOriginTours = data.slice(); // Có thể thay đổi tùy theo lọc
    tours = data.slice(); // Có thể thay đổi

    // Lọc tours theo destination từ trang chủ
    if (destinationFilter && destinationFilter !== '') {
      tours = tours.filter(tour => tour.destination.id == destinationFilter);
      presentOriginTours = tours.slice();
    }

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

////////////////////////////////////////

/*SORT TOURS*/
function funcSortByPriceDec() {
  tours.sort(function (a, b) {
    return b.priceForAdult - a.priceForAdult;
  });
}

function funcSortByPriceInc() {
  tours.sort(function (a, b) {
    return a.priceForAdult - b.priceForAdult;
  });
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
        funcSortByPriceDec();
      } else if (sortPriceBlock.value === 'ascending') {
        funcSortByPriceInc();
      } else {
        tours = presentOriginTours.slice();
      }
      currentPage = 1;
      renderTours(tours.slice(0, perPage), fatherBlock);
      renderToursPagesNumber(fatherBlock);
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
      sortPriceBlock.value = '';
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

async function funcSortByRatingDec() {
  tours = (await Promise.all(
    tours.map(async (tour) => {
      return {
        tour,
        reviewScore: await getReviewScore(`/api/v1/tour-reviews/tours/${tour.id}`),
      };
    })
  )).sort((a, b) => b.reviewScore - a.reviewScore).map(item => item.tour);
}

async function funcSortByRatingInc() {
  tours = (await Promise.all(
    tours.map(async (tour) => {
      return {
        tour,
        reviewScore: await getReviewScore(`/api/v1/tour-reviews/tours/${tour.id}`),
      };
    })
  )).sort((a, b) => a.reviewScore - b.reviewScore).map(item => item.tour);
}

async function SortByReviewScore() {
  try {
    if (sortPriceBlock)
      sortPriceBlock.value = '';
    else
      throw new Error(">>> Element with id '#sort_price' not found in the DOM");

    const fatherBlock = document.querySelector("#tours_list");
    if (fatherBlock) {
      if (sortRatingBlock.value === 'decrease') {
        await funcSortByRatingDec();
      } else if (sortRatingBlock.value === 'ascending') {
        await funcSortByRatingInc();
      } else {
        tours = presentOriginTours.slice();
      }
      currentPage = 1;
      renderTours(tours.slice(0, perPage), fatherBlock);
      renderToursPagesNumber(fatherBlock);
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
      sortRatingBlock.value = '';
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

////////////////////////////////////////

/*TOURS FILTER*/
async function funcToursFilter() {
  try {
    tours = originTours.slice();
    const filterDestination = document.querySelector("#destinationsDropList .dd-option-selected .dd-option-value");
    const filterTOT = document.querySelector("#typeOfTourDropList .dd-option-selected .dd-option-value");

    if (filterDestination && filterDestination.value !== '0') {
      tours = tours.filter(tour => {
        return tour.destination.id === filterDestination.value;
      });
    }
    if (filterTOT && filterTOT.value !== '0') {
      tours = tours.filter(tour => {
        return tour.typeOfTour.id === filterTOT.value;
      });
    }
    if (filterDatePick && filterDatePick.value !== '') {
      let temp = [];

      for (const tour of tours) {
        let departureDays = await getApi(`/api/v1/departure-days/tours/${tour.id}`);
        departureDays = departureDays.filter(departureDay => departureDay.status && compareDates(departureDay.departureDay, filterDatePick.value));
        if (departureDays.length > 0) {
          temp.push(tour);
        }
      }
      tours = temp;
    }

    if (JSON.stringify(tours) != JSON.stringify(presentOriginTours)) {
      if (sortPriceBlock.value != '') {
        SortByPrice(sortPriceBlock);
      } else if (sortRatingBlock.value != '') {
        SortByReviewScore(sortRatingBlock);
      }
      currentPage = 1;
      renderTours(tours.slice(0, perPage), fatherBlock);
      renderToursPagesNumber(fatherBlock);
      presentOriginTours = tours.slice();
    }
  } catch (error) {
    console.log(">>> Error: " + error.message);
  }
}

function handleToursFilter(selector) {
  try {
    fatherBlock = document.querySelector(selector);
    if (filterDatePick)
      filterDatePick.value = '';

    if (fatherBlock) {
      if (filterBtn) {
        filterBtn.addEventListener("click", function () {
          funcToursFilter(fatherBlock, filterDatePick);
        });
      }
    }

    // Setup filter theo destination từ trang chủ
    if (destinationFilter && destinationFilter != '') {
      let options = document.querySelectorAll("#destinationsDropList .dd-option-value");
      for (const option of options) {
        if (option.value == '0')
          option.parentNode.classList.remove("dd-option-selected")
        if (option.value == destinationFilter) {
          option.parentNode.classList.add("dd-option-selected");
          document.querySelector(".dd-selected-value").value = destinationFilter;
          document.querySelector(".dd-selected-text").textContent = option.parentNode.querySelector(".dd-option-text").textContent;
          break;
        }
      }

      // Xóa trạng thái từ local storage để tránh thực hiện nhiều lần
      localStorage.removeItem("destinationFilter");
    }

  } catch (error) {
    console.log(">>> Error: " + error.message);
  }
}
/*END TOURS FILTER*/

////////////////////////////////////////

import {
    compareDates,
    getApi,
    getDropList,
    getReviewScore,
    handleGetData,
    moneyFormat,
    renderRating,
    renderSearchDropList
} from './global_function.js';

async function start() {
  handleGetData("/api/v1/tours", handleRenderTours, "#tours_list");
  Promise.all([getDropList("/api/v1/destinations", renderSearchDropList, "#destinationsDropList", "walking.png"), getDropList("/api/v1/types-of-tours", renderSearchDropList, "#typeOfTourDropList", "all_tours.png")]).then(data => handleToursFilter("#tours_list")).catch(error => console.log(">>> Error: " + error.message));
}

document.addEventListener("DOMContentLoaded", function () {
  start();
})
