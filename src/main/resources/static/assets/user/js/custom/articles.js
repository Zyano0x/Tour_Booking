/* ARTICLES AND PAGINATION */
let articles = [];
let currentPage = 1;
let perPage = 5;
let originArticles = [];
let presentOriginArticles = [];
const sortTimeBlock = document.getElementById("sort_time");
const sortRatingBlock = document.getElementById("sort_rating");

async function renderArticles(perArticles, fatherBlock) {
    try {
        let htmls = '';
        for (const perArticle of perArticles) {
            if (perArticle.status) {
                htmls +=
                    `<div class="strip_all_tour_list wow fadeIn" data-wow-delay="0.1s">
                        <div class="row">
                            <div class="col-lg-4 col-md-4">
                                <div class="img_list">
                                    <a href="/articles/${perArticle.id}"
                                    ><img src="${perArticle.thumbnail}" alt="${perArticle.title}" />
                                    <div class="short_info"><i class="icon_set_1_icon-4"></i></div>
                                    </a>
                                </div>
                            </div>
                            <div class="col-lg-6 col-md-6">
                                <div class="article_list_desc list_desc">
                                    <div class="rating">
                                        ${await renderRating(`/api/article/${perArticle.id}/article-reviews`)}
                                    </div>
                                    <h4><strong>${perArticle.title}</strong></h4>
                                    <p>${perArticle.description}</p>
                                </div>
                            </div>
                            <div class="col-lg-2 col-md-2">
                                <div class="price_list">
                                    <div>
                                    ${dateFormatConvert1(perArticle.dateOfPosting)}<span
                                        class="normal_price_list"
                                    ></span
                                    <p>
                                        <a href="/articles/${perArticle.id}" class="btn_1">Chi tiết</a>
                                    </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--End strip -->`;
            }
        }
        fatherBlock.innerHTML = htmls;
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}

function renderArticlesPagesNumber(fatherBlock) {
    try {
        const pagination = document.querySelector("ul.pagination");
        if (!pagination)
            throw new Error(`>>> Element with selector 'ul.pagination' not found in the DOM`);

        let totalPage = Math.ceil(articles.length / perPage);
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
                renderArticles(articles.slice(
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

async function handleRenderArticles(data, fatherBlock) {
    try {
        originArticles = data.slice(); // Không thay đổi
        presentOriginArticles = data.slice(); // Có thể thay đổi tùy theo tìm kiếm
        articles = data.slice(); // Có thể thay đổi

        // render pages number
        renderArticlesPagesNumber(fatherBlock);

        renderArticles(articles.slice(
            (currentPage - 1) * perPage, (currentPage - 1) * perPage + perPage
        ), fatherBlock);
        handleSortByTime();
        handleSortByReviewScore();
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}

async function handleGetArticles(callBack, fatherBlockSelector) {
    try {
        const fatherBlock = document.querySelector(fatherBlockSelector);
        if (fatherBlock) {
            await getApi("/api/articles/all", "NotCallBack")
                .then(data => callBack(data.reverse(), fatherBlock))
                .catch(error => { throw new Error(error) });
        } else {
            throw new Error("Element not found in the DOM");
        }
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}
/* END ARTICLES AND PAGINATION */

////////////////////////////////////////

/*SORT ARTICLES*/
function funcSortByTimeDec() {
    articles.sort(function (a, b) {
        return compareDates(b.dateOfPosting, a.dateOfPosting);
    });
}

function funcSortByTimeInc() {
    articles.sort(function (a, b) {
        return compareDates(a.dateOfPosting, b.dateOfPosting);
    });
}

function SortByTime() {
    try {
        if (sortRatingBlock)
            sortRatingBlock.value = '';
        else
            throw new Error(">>> Element with id '#sort_time' not found in the DOM");

        const fatherBlock = document.querySelector("#articles_list");
        if (fatherBlock) {
            if (sortTimeBlock.value === 'ascending') {
                funcSortByTimeInc();
            } else {
                funcSortByTimeDec();
            }
            currentPage = 1;
            renderArticles(articles.slice(0, perPage), fatherBlock);
            renderArticlesPagesNumber(fatherBlock);
        } else {
            throw new Error(">>> Element with id '#articles_list' not found in the DOM");
        }
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}

function handleSortByTime() {
    try {
        if (sortTimeBlock) {
            sortTimeBlock.value = '';
            sortTimeBlock.addEventListener("change", function () {
                SortByTime();
            });
        } else {
            throw new Error(">>> Element with id '#sort_price' not found in the DOM");
        }
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}

async function funcSortByRatingDec() {
    articles = (await Promise.all(
        articles.map(async (article) => {
            return {
                article,
                reviewScore: await getReviewScore(`/api/article/${article.id}/article-reviews`),
            };
        })
    )).sort((a, b) => b.reviewScore - a.reviewScore).map(item => item.article);
}

async function funcSortByRatingInc() {
    articles = (await Promise.all(
        articles.map(async (article) => {
            return {
                article,
                reviewScore: await getReviewScore(`/api/article/${article.id}/article-reviews`),
            };
        })
    )).sort((a, b) => a.reviewScore - b.reviewScore).map(item => item.article);
}

async function SortByReviewScore() {
    try {
        if (sortTimeBlock)
            sortTimeBlock.value = '';
        else
            throw new Error(">>> Element with id '#sort_price' not found in the DOM");

        const fatherBlock = document.querySelector("#articles_list");
        if (fatherBlock) {
            if (sortRatingBlock.value === 'decrease') {
                await funcSortByRatingDec();
            } else if (sortRatingBlock.value === 'ascending') {
                await funcSortByRatingInc();
            } else {
                articles = presentOriginArticles.slice();
            }
            currentPage = 1;
            renderArticles(articles.slice(0, perPage), fatherBlock);
            renderArticlesPagesNumber(fatherBlock);
        } else {
            throw new Error(">>> Element with id '#articles_list' not found in the DOM");
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
/*END SORT ARTICLES*/

////////////////////////////////////////

/*ARTICLES SEARCH*/
const searchInput = document.querySelector(".search-input");

function funcArticlesSearch(fatherBlock) {
    try {
        articles = originArticles.slice();

        if (searchInput && searchInput.value != '') {
            articles = articles.filter(article => {
                return article.title.toLowerCase().includes(searchInput.value.toLowerCase());
            });
        }

        if (JSON.stringify(articles) != JSON.stringify(presentOriginArticles)) {
            currentPage = 1;
            if (sortTimeBlock.value != '') {
                SortByTime();
            } else if (sortRatingBlock.value != '') {
                SortByReviewScore();
            }
            currentPage = 1;
            renderArticles(articles.slice(0, perPage), fatherBlock);
            renderArticlesPagesNumber(fatherBlock);
            presentOriginArticles = articles.slice();
        }
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}

function handleArticlesFilter(selector) {
    try {
        const fatherBlock = document.querySelector(selector);
        if (searchInput)
            searchInput.value = '';

        if (fatherBlock) {
            const searchBtn = document.querySelector(".search-button");
            if (searchBtn) {
                searchBtn.addEventListener("click", async function () {
                    funcArticlesSearch(fatherBlock);
                });
            }
        }
    } catch (error) {
        console.log(">>> Error: " + error.message);
    }
}
/*END ARTICLES SEARCH*/

////////////////////////////////////////

import {compareDates, dateFormatConvert1, getApi, getReviewScore, renderRating} from './global_function.js';

function start() {
    handleGetArticles(handleRenderArticles, "#articles_list");
    handleArticlesFilter("#articles_list");
}

document.addEventListener("DOMContentLoaded", function () {
    start();
})