import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {LoginService} from "./login/LoginService/LoginService";

@Injectable({providedIn: 'root'})
export class BasicGuard implements CanActivate {
  constructor(
    private router: Router,
    private loginService: LoginService
  ) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    console.log("guard" + this.loginService.currentRole);
    if (this.loginService.currentRole === "NONE") {
      this.router.navigateByUrl("/login");
      return false;
    } else {
      return true;
    }
  }
}
