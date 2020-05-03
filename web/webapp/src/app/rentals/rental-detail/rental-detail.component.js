"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require('@angular/core');
require('rxjs/add/operator/switchMap');
var RentalDetailComponent = (function () {
    function RentalDetailComponent(rentalService, route, location) {
        this.rentalService = rentalService;
        this.route = route;
        this.location = location;
    }
    RentalDetailComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params
            .switchMap(function (params) { return _this.rentalService.getRental(+params['id']); })
            .subscribe(function (rental) { return _this.rental = rental; });
    };
    RentalDetailComponent.prototype.goBack = function () {
        this.location.back();
    };
    __decorate([
        core_1.Input()
    ], RentalDetailComponent.prototype, "rental", void 0);
    RentalDetailComponent = __decorate([
        core_1.Component({
            selector: 'app-rental-detail',
            templateUrl: './rental-detail.component.html',
            styleUrls: ['./rental-detail.component.css'],
        })
    ], RentalDetailComponent);
    return RentalDetailComponent;
}());
exports.RentalDetailComponent = RentalDetailComponent;
