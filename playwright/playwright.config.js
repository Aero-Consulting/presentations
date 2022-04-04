// playwright.config.js
// @ts-check

const {devices} = require("@playwright/test");
/** @type {{projects: [{use: {viewport: {headless: boolean, width: number, height: number}, browserName: string}, name: string},{use: {headless: boolean, browserName: string}, name: string},{use: {headless: boolean, browserName: string}, name: string},{hasTouch: boolean, viewport: ViewportSize, name: string, deviceScaleFactor: number, userAgent: string, isMobile: boolean, defaultBrowserType: "chromium" | "firefox" | "webkit"},{hasTouch: boolean, viewport: ViewportSize, name: string, deviceScaleFactor: number, userAgent: string, isMobile: boolean, defaultBrowserType: "chromium" | "firefox" | "webkit"}], use: {baseURL: string, screenshot: string, video: string}, reporter: string}} */

const config = {
    use: {
        baseURL: 'https://playwright.dev', screenshot: 'only-on-failure', video: 'on', trace: 'on', //trace: 'on-first-retry',
        //video: 'on-first-retry',
        //reporter: [ ['json', { outputFile: 'results.json' }] ],
        reporter: [["line"], ["allure-playwright"], ["html", {open: 'never'}]], workers: 2, retries: 2,
    }, projects: [{
        name: 'Desktop Chromium', use: {
            browserName: 'chromium', viewport: {
                width: 1920, height: 1080, headless: true
            },
        }
    }, {
        name: 'Desktop Firefox', use: {browserName: 'firefox', headless: true},
    },

        {
            name: 'Desktop Safari', use: {browserName: 'webkit', headless: false},
        }, {
            name: 'Pixel 5', use: {browserName: 'chromium', ...devices['Pixel 5']},
        }, {
            name: 'iPhone 12', use: {
                browserName: 'webkit', ...devices['iPhone 12'],
            },
        },],
};

module.exports = config;
