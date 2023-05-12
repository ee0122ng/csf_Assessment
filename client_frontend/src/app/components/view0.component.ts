import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-view0',
  templateUrl: './view0.component.html',
  styleUrls: ['./view0.component.css']
})
export class View0Component {

  constructor(private router: Router) {}

  uploadImage() {
    this.router.navigate(['upload'])
  }

}
