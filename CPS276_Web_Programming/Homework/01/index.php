<?php
include 'security.php';
secure_my_page();
/**
 *		CPS 276 Winter 2016-- Assignment 01
 * 	Aquarium Cost Estimator
 */

// first, some notes...

// <-- Comments in PHP can be added with two forward slashes -- the comment continues until the next line
#  <-- Or with an octothorp (hash sign)
/* <-- Or for mutiple lines,
		 sandwiched between these characters -->  */

		 
// Variables are identified with a dollar sign:
$a = 5;

// -------------------------------------------------------

// Your assignment is to calculate the following values...

$materials_cost = 0;		// cost of materials: glass, fish, water, etc
$labor_cost = 0;			// cost of putting it together
$total_cost = 0;			// the grand total
$time_required = 0;		// the total number of hours required
$total_fish = 0;			// total fish in a fully-stocked tank
$regular_fish = 0;
$fancy_fish = 0;

// here is the input... (no changes needed here)

$width = intval(@$_REQUEST['width']);
$height = intval(@$_REQUEST['height']);
$depth = intval(@$_REQUEST['depth']);


// you can set the following variable to a string, to report any errors at the end.
//$error='';

$continue = FALSE;
// validate width
if ( $width <= 0 ) {
    $error = 'Width must be greater than zero.'; 

// validate height
} else if ( $height <= 0 ) {
	$error = 'Height must be greater than zero.'; 

// validate depth
} else if ( $depth <= 0 ) {
	$error = 'Depth must be greater than zero.';
} else {
	$error='';
	$continue = TRUE;
}
// Example: $error='Width cannot be a negative number.';

	
// START WORKING HERE...

// Part 1: Materials Cost

/*		
		A. Start by finding the total surface area of the aquarium. Assume that the aquarium is glass on all six sides. Store this as $glass_surface. Hint: (W*H)*2 + (W*D)*2 + (H*D)*2
		B. Also find the total volume of the aquarium, and store as $total_volume. Assume the tank will be filled completely to the top.
		C. Raw materials cost: glass costs $0.03 per cm/sq.
		D. Raw materials cost: purified water costs $0.001 per cm3 (cubic centimeter).
		E. Each fish requires 275 cm3 of space. Find the maximum number of fish that can fit in the tank. ** Hint: use the floor() function to round down.
		F. NOT more than 7% of the fish will be fancy fish ($1.98 each). There must be an even number of fancy fish. Add as many fancy fish as possible. The rest will be regular fish ($0.61 each).
		G. Add a small castle and lighting: $7.95
		H. The sum of C,D,F,and G and the total materials cost. 
*/
if ($continue){
$glass_surface = ($width*$height)*2 + ($width*$depth)*2 + ($height*$depth)*2;
$total_volume = $width*$depth*$height;

$total_fish = $total_volume/275;
$total_fish = floor($total_fish);
$fancy_fish = $total_fish * 0.07;
$fancy_fish = floor($fancy_fish);

//must have an even amount of fancy fish
if (!(($fancy_fish % 2) === 0)){
	$fancy_fish -= 1;
}

// in case total fish is < 1
if ($total_fish < 1){
	$total_fish = 0;
	$fancy_fish = 0;
}

$regular_fish = $total_fish - $fancy_fish;

$materials_cost = 
//cost of glass
($glass_surface * 0.03) +
//cost of water
($total_volume * 0.001) +
//cost of fancy fish
($fancy_fish * 1.98) +
//cost of regular fish
($regular_fish * 0.61) +
//cost of small castle and lighting
7.95;
}


// Part 2: Time Required

/*
		The time required will be used to calculate the labor costs
		A. There are two times to keep track of: time to construct the tank ($tank_time) and time to stock the fish ($stock_time), both measured in seconds.
		B. For the tank time, use the FOR loop below.
		   Start with x= the total volume of the tank (cm3)
		   The first side takes x seconds to construct
			Each successive side takes 10% less time than the previous
		C. Stock time is 3 seconds per fish.
		D. the total time is the sum of the tank time and the stock time, measured in minutes.
*/
if ($continue){
$tank_time=0;
$stock_time=0;

// tank time
$x = $total_volume;
for($i = 0; $i < 6; $i++){
	// {add code here}
	$tank_time += $x;
	$x -= ($x * 0.1);
}

$stock_time = $total_fish * 3;
$time_required = ceil(($tank_time + $stock_time) / 60);
}

// Part 3: Labor Costs and Grand Total

/* 	A. The staff are aquarium professionals, paid $33.71 per hour or portion thereof. ** Hint: round up to the nearest hour with the ceil() function
		B. Add $12 handling fee.
		C. Calculate the grand total (materials and labor and fees)
		D. There's a sale going on! If the total is greater than $250, give a 30% discount. If between $100-$249, give a 10% discount.
		E. Use the number_format function to alter the math precision for currency.
			Example: $amount = number_format($amount, 2);   -->  would turn 24.022278 into 24.02
*/

if($continue){
$labor_cost = (33.71 * ceil($time_required/60));
$total_cost = 12 + $materials_cost + $labor_cost;

if ($total_cost >= 100 && $total_cost < 250){
	$reduced_cost = $total_cost - ($total_cost * 0.1);
	$total_cost = '$' . number_format($total_cost, 2) 
	.'($'.number_format($reduced_cost, 2) . ' after discount)';
}
else if ($total_cost >= 250){
	$reduced_cost = $total_cost - ($total_cost * 0.3);
	$total_cost = '$' . number_format($total_cost, 2)  
	.'($'.number_format($reduced_cost, 2) . ' after discount)';
}
else
	$total_cost = '$' . number_format($total_cost, 2);
	
$materials_cost = '$' . number_format($materials_cost, 2);
$labor_cost = '$' . number_format($labor_cost);
}



/*		What follows is the HTML form used to present the application.
 		No changes are needed beyond this point, but it's a good idea to look this over.
 		You will be making your own forms in future assignments. */

echo $error;
if($width==0) $width='';
if($height==0) $height='';
if($depth==0) $depth='';
?>
<form method='get' action='index.php'>
	<table border='1'>
		<tr>
			<th colspan='2'>Aquarium Cost Estimator</th>
		</tr>
		<tr>
			<td>Width</td>
			<td><input type='text' name='width' value='<?=$width?>' size='5'/> cm</td>
		</tr>
		<tr>
			<td>Height</td>
			<td><input type='text' name='height' value='<?=$height?>' size='5'/> cm</td>
		</tr>
		<tr>
			<td>Depth</td>
			<td><input type='text' name='depth' value='<?=$depth?>' size='5'/> cm</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><input type='submit' value='Calculate'/></td>
		</tr>
		<tr>
			<th colspan='2'>Results</th>
		</tr>
		<tr>
			<td>Total Fish</td>
			<td><?php if($total_fish) echo $total_fish.' ('.$fancy_fish.' fancy)'; else echo '&nbsp;'?></td>
		</tr>
		<tr>
			<td>Materials Cost</td>
			<td><?=($materials_cost) ? $materials_cost: '&nbsp;'?></td>
		</tr>
		<tr>
			<td>Labor Cost</td>
			<td><?=($labor_cost) ? $labor_cost : '&nbsp;'?></td>
		</tr>
		<tr>
			<td>Total Cost</td>
			<td><?=($total_cost) ? $total_cost : '&nbsp;'?></td>
		</tr>
		<tr>
			<td>Time Required</td>
			<td><?=($time_required)?$time_required.' minutes':'&nbsp;'?></td>
		</tr>
	</table>
</form>




