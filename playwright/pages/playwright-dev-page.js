const {expect} = require('@playwright/test');

exports.PlaywrightDevPage = class PlaywrightDevPage {

    /**
     * @param {import('@playwright/test').Page} page
     */
    constructor(page) {
        this.page = page;
        this.getStartedLink = page.locator('text=Get started');
        this.installationLink = page.locator('div.markdown li a[href="#installation"]');
        this.tocList = page.locator('article ul > li > a');

    }

    async goto() {
        await this.page.goto('/');
    }

    async getStarted() {
        await this.getStartedLink.first().click();
        await expect(this.installationLink).toBeVisible();
    }

    async coreConcepts() {
        await this.getStarted();
        await this.page.click('text=Installation');
        await this.installationLink.click();
        await expect(this.page.locator('h2').locator("text=Installation")).toBeVisible();
    }
};
