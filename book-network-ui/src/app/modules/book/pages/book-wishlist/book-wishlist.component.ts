import {Component, OnInit} from '@angular/core';
import {BookService} from "../../../../services/services/book.service";

@Component({
  selector: 'app-book-wishlist',
  templateUrl: './book-wishlist.component.html',
  styleUrls: ['./book-wishlist.component.scss']
})
export class BookWishlistComponent {
  name: String;
  age: number = 19;
  size: number = 41;
  employye: boolean = false;

  constructor() {
    this.name = "Hasan"
  }

}
