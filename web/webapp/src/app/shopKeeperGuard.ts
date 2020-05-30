import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {LoginService} from "./login/LoginService/LoginService";

@Injectable({providedIn: 'root'})
export class ShopKeeperGuard implements CanActivate {
  constructor(
    private router: Router,
    private loginService: LoginService
  ) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    console.log("shopkeeper guard" + this.loginService.currentRole);
    if (this.loginService.currentRole !== "ROLE_SHOPKEEPER") {
      this.router.navigateByUrl("/home");
      return false;
    } else {
      return true;
    }
  }
}
